package com.ns.warlock.controller.wechat;

import com.alibaba.fastjson.JSONArray;
import com.ns.warlock.common.ErrorCode;
import com.ns.warlock.common.Result;
import com.ns.warlock.dto.*;
import com.ns.warlock.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户购物车信息
 */
@CrossOrigin
@RestController
@RequestMapping("/wechat/cart")
public class WechatCartController extends BaseController {

    @Resource(name= "cartServiceImpl")
    private CartService cartService;

    @Resource(name = "cartItemServiceImpl")
    private CartItemService cartItemService;

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "snServiceImpl")
    private SnService snService;

    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;

    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

    /**
     * 获取购物车里面所有购物项目
     * @param memberId
     * @return
     */
    @PostMapping(value = "/extractAllCart")
    @ApiOperation(value = "用户购物车信息", notes = "根据用户ID获取购物车信息和商品信息")
    public @ResponseBody
    Result extractAllCart(@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam Long memberId) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
        result.setData(cartItemService.findAll(memberId));
        return result;
    }


    /**
     * 更新购物车数量
     * @param cartItemId
     * @param toggle
     * @return
     */
    @Transactional
    @PostMapping(value = "/updateProductQuality")
    @ApiOperation(value = "更新商品数量", notes = "更新购物车内商品数量")
    public @ResponseBody Result updateProductQuality(@ApiParam(required = true, name = "cartItemId", value = "购物车项ID")@RequestParam long cartItemId,
                                                     @ApiParam(required = true, name = "toggle", value = "增加还是减少 -1: 减少 1：增加")@RequestParam int toggle) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
        try {
            if (toggle == -1) {
                cartItemService.subQuantity(cartItemId);
            } else {
                cartItemService.addQuantity(cartItemId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("1");
            result.setMessage("购物车更新失败,请重试");
        }
        return result;
    }


    /**
     * 清理购物车
     * @param ids
     * @return
     */
    @PostMapping(value = "/clearCart")
    @ApiOperation(value = "清理购物车", notes = "根据购物车ID清理购物车" )
    public @ResponseBody Result clearCart(Long[] ids) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
        try {
            cartItemService.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("1");
            result.setMessage("清理购物车失败,请重试");
        }
        return result;
    }

    /**
     * 添加至购物车
     * @param productId
     * @param memberId
     * @return
     */
    @PostMapping(value = "/addInCart")
    @ApiOperation(value = "添加进购物车", notes = "添加商品到购物车")
    public @ResponseBody Result addInCart(@ApiParam(required = true, name = "productId", value = "商品ID")@RequestParam long productId,
                                          @ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId) {

        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);


        ProductDTO productDTO = productService.find(productId);
        CartDTO cartDTO = null;
        CartItemDTO cartItemDTO = null;
        try {

            //检查当前用户有没有购物车
            cartDTO = cartService.find(memberId);
            if (cartDTO != null) {//有购物车

                //检查当前用户有没有此商品
                Map<String, Long> param = new HashMap<>();
                param.put("productId", productId);
                param.put("cartId", cartDTO.getId());
                cartItemDTO = cartItemService.checkProductExist(param);

                if (cartItemDTO != null) {//有此商品 =》 增加数量
                    cartItemService.addQuantity(cartDTO.getId());
                }
            } else {//没有
                cartDTO = new CartDTO();
                cartDTO.setMemberId(memberId);
                cartDTO.setCreateDate(new Date());
                cartService.insert(cartDTO);
            }


            //最终都会新增加一条购物车项记录
            if (cartItemDTO == null) {
                //新增一条记录
                cartItemDTO = new CartItemDTO();
                cartItemDTO.setCartId(cartDTO.getId());
                cartItemDTO.setProductId(productId);
                cartItemDTO.setQuantity(new BigDecimal(1));
                cartItemDTO.setPrice(productDTO.getPrice());
                cartItemDTO.setCreateDate(new Date());
                cartItemService.insert(cartItemDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("1");
            result.setMessage("添加购物车失败,请重试");
        }
        return result;
    }

    /**
     * 由购物车生成一个订单和子订单项
     * @param cartItemIds
     * @return
     */
    @PostMapping(value = "/generateOrder")
    @ApiOperation(value = "生成订单", notes = "根据购物车项生成订单")
    public @ResponseBody Result generateOrder (
            @ApiParam(required = true, name = "memberId", value = "会员ID")@RequestParam String memberId,
            @ApiParam(required = true, name = "addressId", value = "地址ID")@RequestParam String addressId,
            @ApiParam(required = false, name = "memo", value = "附言")@RequestParam(required = false) String memo,
            @ApiParam(required = true, name = "cartItemIds", value = "购物车项id数组")@RequestParam Long[] cartItemIds) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);

        List<CartItemDTO> cartItemDTOList = cartItemService.findList(memberId, cartItemIds);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMemberId(memberId);
        orderDTO.setAddressId(addressId);

        if (StringUtils.isNotEmpty(memo)) orderDTO.setMemo(memo);

        if (cartItemDTOList.isEmpty()) {
            result.setCode(ERROR_CODE);
            result.setMessage(ErrorCode.ORDER_ITEM_REQUIRED.getErrorValue());
        } else {
            //获取订单编号
            String orderSn = snService.generate(SnDTO.Type.order);

            //循环去更改订单每一项内容
            //并获取订单总价
            List<OrderItemDTO> orderItemDTOList = cartItemDTOList.stream()
                    .map(cartItemDTO -> {

                        OrderItemDTO orderItem = new OrderItemDTO();
                        orderItem.setCreateDate(new Date()); //时间
                        orderItem.setProductId(cartItemDTO.getProductId());     //商品id
                        orderItem.setOrderPrice(cartItemDTO.getPrice());        //交易价格
                        orderItem.setQuantity(cartItemDTO.getQuantity());       //商品数量

                        //计算总费用 单价*数量
                        BigDecimal tmpTotalPrice = cartItemDTO.getPrice().multiply(cartItemDTO.getQuantity());
                        orderItem.setItemTotalPrice(tmpTotalPrice);

                        return orderItem;

                    }).collect(Collectors.toList());

            orderDTO.setOrderSn(orderSn);//订单编号
            orderDTO.setOrderStatus(OrderDTO.OrderStatus.confirmed.ordinal()); //订单确认
            orderDTO.setCreateDate(new Date());

            BigDecimal totalPayDecimal = new BigDecimal(0);
            for (OrderItemDTO orderItemDTO : orderItemDTOList) {
                totalPayDecimal = totalPayDecimal.add(orderItemDTO.getItemTotalPrice());
            }
            orderDTO.setTotalAmount(totalPayDecimal); //订单交易总金额
            //新建订单
            orderService.create(orderDTO);

            //获取生成的orderId
            long orderId = orderDTO.getId();

            //不在流里面进行循环持久化订单子项目
            for(OrderItemDTO orderItemDTO : orderItemDTOList) {
                orderItemDTO.setOrderId(orderId);
                orderItemService.create(orderItemDTO);
            }

            //删除购物车里面的子项目
            cartItemService.delete(cartItemIds);
        }

        return result;
    }

}
