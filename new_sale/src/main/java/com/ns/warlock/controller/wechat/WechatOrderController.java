package com.ns.warlock.controller.wechat;

import com.alibaba.fastjson.JSONArray;
import com.ns.warlock.common.ErrorCode;
import com.ns.warlock.common.Result;
import com.ns.warlock.dto.*;
import com.ns.warlock.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * 用户订单
 */
@RestController
@RequestMapping("/wechat/order/")
@CrossOrigin
public class WechatOrderController extends BaseController {

    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "memberLevelServiceImpl")
    private MemberLevelService memberLevelService;

    @Resource(name = "groupServiceImpl")
    private GroupService groupService;

    @Resource(name = "snServiceImpl")
    private SnService snService;

    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;


    /**
     * 获取用户订单列表
     * @param memberId
     * @return
     */
    @PostMapping(value = "/extractOrderList")
    @ApiOperation(value = "获取用户订单", notes = "获取用户所有订单")
    public @ResponseBody
    Result extractOrderList(@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam long memberId) {
        Result result = new Result("0", SUCCESS_MESSAGE);
        result.setData(orderService.findByMemberId(memberId));
        return result;
    }

    /**
     * 取消订单
     * @param ids
     * @return
     */
    @PostMapping(value = "/cancelOrder")
    @ApiOperation(value = "取消订单", notes = "取消用户所选的订单,用户只能取消未支付的订单，已经支付和已经完成的订单无法被取消")
    public @ResponseBody Result cancelOrder(@ApiParam(required = true, name = "memberId", value = "会员ID")@RequestParam String memberId,
                                            @RequestParam Long[] ids) {
        Result result = new Result("0", SUCCESS_MESSAGE);
        orderService.cancel(memberId, ids);

        return result;
    }


    /**
     * 支付订单
     * @param memberId
     * @param orderIds
     * @return
     */
    @Transactional
    @PostMapping(value = "/pay")
    @ApiOperation(value = "支付订单", notes = "支付订单,支付订单需要传memberId和订单id数组")
    public @ResponseBody Result pay(@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId,
                                    @ApiParam(required = true, name = "orderIds", value = "订单Id")@RequestParam String[] orderIds){
            //,@ApiParam(required = true, name = "totalPayDecimal", value = "订单总支付金额")@RequestParam BigDecimal totalPayDecimal) {

        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);

        if (orderIds.length == 0) {
            result.setCode(ERROR_CODE);
            result.setMessage(ErrorCode.DUPLICATE_ERROR.getErrorValue());
        } else {

            //检查订单在不在该用户下 暂时未做！！！

            //用于存放商品和商品数量方便后面做UPDATE
            Map<Long, BigDecimal> prdQuanMap = new HashMap<>();

            //循环订单编号列表 => 这里需要优化
            BigDecimal ownerOrderTotalBigDecimal = new BigDecimal(0); //个人支付总金额
            for (String orderId : orderIds) {
                //获取当前订单信息并累加最终支付金额
                OrderDTO orderDTO = orderService.find(Long.parseLong(orderId));
                //循环获取个人应该支付的金额
                ownerOrderTotalBigDecimal = ownerOrderTotalBigDecimal.add(orderDTO.getTotalAmount());

                //更改订单状态
                orderDTO.setId(Long.parseLong(orderId));
                orderDTO.setOrderStatus(OrderDTO.OrderStatus.completed.ordinal()); //改为已经支付
                orderDTO.setPayDate(new Date()); //设置支付时间
                orderDTO.setMemberId(memberId);  //设置购买商品的会员
                orderService.update(orderDTO);

                //这里对已经支付的商品 设置已经分配的库存
                List<OrderItemDTO> orderItemDTOList = orderItemService.findList(memberId, Long.parseLong(orderId));
                for (OrderItemDTO item : orderItemDTOList) {
                    if (!prdQuanMap.containsKey(Long.valueOf(item.getProductId()))) {
                        prdQuanMap.put(Long.valueOf(item.getProductId()), item.getQuantity());
                    } else {
                        BigDecimal newQuantity = prdQuanMap.get(Long.valueOf(item.getProductId())).add(item.getQuantity());
                        prdQuanMap.replace(Long.valueOf(item.getProductId()), newQuantity);
                    }
                }
            }

            //获取当前用户信息
            MemberDTO memberDTO = memberService.find(Long.parseLong(memberId));

            //获取改金额属于哪一个用户等级
            String newMemberLevel = memberLevelService.selectPromptLevel(ownerOrderTotalBigDecimal);
            if (newMemberLevel != memberDTO.getMemberLevel()) {
                //memberDTO
            }

            //支付金额累加至个人
            //if (Objects.nonNull(memberDTO.getOwnerCoast())) ownerTotalBigDecimal = memberDTO.getOwnerCoast();
            BigDecimal ownerTotalBigDecimal = ownerOrderTotalBigDecimal.add(memberDTO.getOwnerCoast());
            memberDTO.setOwnerCoast(ownerTotalBigDecimal);

            //并循环递增至父一级节点数据
            if (StringUtils.isEmpty(memberDTO.getParentOpenId())) { //第一级节点
                return result;
            } else { //非第一级节点

                List<MemberDTO> memberDTOList = new ArrayList<>();

                //先把当前用户的信息更新
                memberDTOList.add(memberDTO);

                //循环获取上一级并更新金额
                long parentId = memberDTO.getParentId();
                boolean needLoop = true;
                do {
                    //获取上一级节点数据
                    MemberDTO parentMemberDTO = memberService.find(parentId);
                    //获取团队销量
                    BigDecimal groupTotalBigDecimal = Objects.nonNull(parentMemberDTO.getLeafCoast()) ? parentMemberDTO.getLeafCoast() : new BigDecimal(0);
                    groupTotalBigDecimal = groupTotalBigDecimal.add(ownerTotalBigDecimal);
                    //累加至团队
                    parentMemberDTO.setLeafCoast(groupTotalBigDecimal);

                    //退出循环
                    if (StringUtils.isEmpty(parentMemberDTO.getParentOpenId())) {
                        needLoop = false;
                    } else {
                        parentId = parentMemberDTO.getParentId();
                        memberDTOList.add(parentMemberDTO);
                    }
                } while (needLoop);

                //用流的形式去处理并获取第一级的会员信息
                Optional<MemberDTO> optional =  memberDTOList.stream().filter(item -> item.getParentId() == 1l).findFirst();

                GroupDTO groupDTO = null;
                //判断是否有值
                if (optional.isPresent()) {
                    MemberDTO tempMemberDTO = optional.get();
                    //并获取第一级会员的团队信息
                    groupDTO = groupService.findByCreateUser(tempMemberDTO.getOpenId());
                    groupDTO.setCoast(tempMemberDTO.getOwnerCoast().add(tempMemberDTO.getLeafCoast()));
                }


                //循环去更新数据信息 并退出
                //？这里对MemberDTO进行排序，并从下到上去更新，单独一个方法并设置方法为原子类型
                synchronized (WechatOrderController.class) {
                    //循环去更改个人销量
                    memberDTOList.stream().forEach(item -> memberService.update(item));

                    //更改团队的销量
                    if (groupDTO != null) {
                        groupService.update(groupDTO);
                    }

                    //这里循环去更新库存
                    Iterator<Long> iterator = prdQuanMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        Long productId = iterator.next();
                        productService.changeStock(productId, prdQuanMap.get(productId));
                    }
                }
            }
        }

        return result;
    }


//    /**
//     * 追踪用户物流信息
//     * @return
//     */
//    public @ResponseBody String traceOrder(@ApiParam(required = true, name = "orderId", value = "订单ID")@RequestParam String orderId) {
//
//
//    }


}
