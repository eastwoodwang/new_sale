package com.ns.warlock.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.ns.warlock.common.Result;

import com.ns.warlock.dto.GroupDTO;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.dto.SaleStatisDTO;
import com.ns.warlock.service.GroupService;
import com.ns.warlock.service.MemberService;
import com.ns.warlock.service.SaleStatisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 获取登录用户统计数据
 */
@RestController
@RequestMapping("/wechat/statis")
@CrossOrigin
public class WechatStatisController extends BaseController {


    @Resource(name = "saleStatisServiceImpl")
    private SaleStatisService saleStatisService;

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "groupServiceImpl")
    private GroupService groupService;


    /**
     * 获取用户当月的销量数据统计
     * @param memberId
     * @return
     */
    @PostMapping(value = "/personCurrentStats")
    @ApiOperation(value = "销售信息", notes = "获取登录用户个人当月销售和团队当月销售信息")
    public @ResponseBody
    Result personCurrentStats (@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);

        //获取当前用户的信息
        MemberDTO memberDTO = memberService.find(Long.parseLong(memberId));
        //获取用户所在团队的信息
        GroupDTO groupDTO = groupService.find(memberDTO.getGroupId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("personSale", memberDTO.getOwnerCoast()); //个人销量
        jsonObject.put("leafSale", memberDTO.getLeafCoast());   //下属销量
        jsonObject.put("groupSale", groupDTO.getCoast()); //所在团队的总销量
        jsonObject.put("currentMonth", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        result.setData(jsonObject);

        return result;
    }


    /**
     * 获取个人销量详情
     *   看个人上一个月的详情，当月在订单里面可见
     * @param memberId
     * @return
     */
    @PostMapping(value = "/personDetail")
    @ApiOperation(value = "个人销售详情", notes = "获取个人的既往销售和团队既往销售")
    public @ResponseBody Result personDetail(@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
        result.setData(saleStatisService.findList(memberId));
        return result;
    }


    /**
     * 看下一级人员的销售情况
     *    上一个月的销售情况
     * @return
     */
    @PostMapping(value = "/leafDetail")
    @ApiOperation(value = "下一级销售详情", notes = "获取所在团队下一级的销售详情")
    public @ResponseBody Result leafDetail(@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId) {
        Result result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
        result.setData(saleStatisService.leafList(memberId));
        return result;
    }

}
