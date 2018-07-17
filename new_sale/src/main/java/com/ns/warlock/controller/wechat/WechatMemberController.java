package com.ns.warlock.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.ns.warlock.common.ErrorCode;
import com.ns.warlock.common.Result;
import com.ns.warlock.dto.GroupDTO;
import com.ns.warlock.dto.MemberAddressDTO;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.service.*;
import com.ns.warlock.util.WeixinUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@RestController("wechatMemberController")
@RequestMapping("/wechat/member")
@Api(value = "微信用户接口")
@CrossOrigin
public class WechatMemberController extends BaseController {

	public static Logger log = LoggerFactory.getLogger(WechatMemberController.class);

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "memberAddressServiceImpl")
    private MemberAddressService memberAddressService;

    @Resource(name = "memberLevelServiceImpl")
    private MemberLevelService memberLevelService;

    @Resource(name = "groupServiceImpl")
    private GroupService groupService;

    @Resource(name = "groupLevelServiceImpl")
    private GroupLevelService groupLevelService;


    /**
     * 获取用户的地址
     * @return
     */
    @PostMapping(value = "/extractAllAddress")
    @ApiOperation(value = "获取用户全部地址", notes = "根据用户ID获取该用户的全部邮寄地址")
    public @ResponseBody Result extractAllAddress (@ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam Long memberId) {
        Result result = new Result("0", SUCCESS_MESSAGE);

        result.setData(memberAddressService.findAll(memberId));

        return result;
    }

    /**
     * 创建新用户地址
     * @param address
     * @param zipCode
     * @param phone
     * @param name
     * @param memberId
     * @return
     */
    @PostMapping(value = "/createMemberAddress")
    @ApiOperation(value = "创建用户收货地址", notes = "创建用户新的收货地址")
    public @ResponseBody Result createMemberAddress (@ApiParam(required = true, name = "address", value = "地址")@RequestParam String address,
                                                     @ApiParam(required = false, name = "zipCode", value = "邮编")@RequestParam(required = false) String zipCode,
                                                     @ApiParam(required = true, name = "phone", value = "手机")@RequestParam String phone,
                                                     @ApiParam(required = true, name = "name", value = "收货人姓名")@RequestParam String name,
                                                     @ApiParam(required = true, name = "memberId", value = "用户ID")@RequestParam String memberId) {
        Result result = new Result("0", SUCCESS_MESSAGE);
        MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
        memberAddressDTO.setCreateDate(new Date());
        memberAddressDTO.setAddress(address);
        memberAddressDTO.setPhone(phone);
        memberAddressDTO.setName(name);
        memberAddressDTO.setMemberId(Long.parseLong(memberId));
        memberAddressService.create(memberAddressDTO);

        return result;
    }

    /**
     * 修改用户地址
     * @param memberAddressDTO
     * @return
     */
    @PostMapping(value = "/updateMemberAddress")
    @ApiOperation(value = "更新用户收货地址", notes = "更新用户新的收货地址")
    public @ResponseBody Result updateMemberAddress (MemberAddressDTO memberAddressDTO) {
        if (memberAddressDTO.getId() <= 0 ) {
            return new Result(ERROR_CODE, ErrorCode.PARAMETER_REQUIRE.getErrorValue());
        }

        memberAddressDTO.setUpdateDate(new Date());
        memberAddressService.update(memberAddressDTO);
        return new Result("0", SUCCESS_MESSAGE);
    }

    /**
     * 获取用户地址
     * @param addressId
     * @return
     */
    @PostMapping(value = "/findMemberAddress")
    @ApiOperation(value = "获取用户收货地址", notes = "根据地址ID获取用户收货地址详情")
    public @ResponseBody Result findMemberAddress (@ApiParam(required = true, name = "addressId", value = "地址ID")@RequestParam Long addressId) {
        return new Result("0", SUCCESS_MESSAGE, memberAddressService.find(addressId));
    }

    /**
     * 删除用户地址
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteMemberAddress")
    @ApiOperation(value = "删除用户收货地址", notes = "根据ids批量删除用户id")
    public @ResponseBody Result deleteMemberAddress (Long[] ids) {

        memberAddressService.delete(ids);

        return new Result("0", SUCCESS_MESSAGE);
    }

    /**
     * 检查用户是否已经注册
     * @param openid
     * @return
     */
    @PostMapping(value = "/checkMemberRegister")
    @ApiOperation(value = "检查用户登录", notes = "根据openid检查用户是否登录并返回用户信息")
    public @ResponseBody
    Result checkMemberRegister(@ApiParam(required = true, name = "openid", value = "微信用户openid")@RequestParam String openid) {
        Result result = new Result();

        //获取用户并返回给前端用户
        MemberDTO memberDTO = memberService.checkMemberRegister(openid);
        if (memberDTO != null) {
            result.setCode(SUCCESS_CODE);
            result.setMessage(SUCCESS_MESSAGE);
            result.setData(memberDTO);
        } else {
            result.setCode("1");
            result.setMessage("用户不存在");
        }

        return result;
    }

    /**
     * 用户注册 检查是否为根用户下第一级用户，如果是则同时创建一个组
     * @param openid
     * @param nickname
     * @param sex
     * @param headImgUrl
     * @param fromOpenId
     * @return
     */
    @Transactional
    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册", notes = "获取微信用户权限，注册新用户")
    public @ResponseBody Result register(@ApiParam(required = true, name = "openid", value = "微信用户openid")@RequestParam String openid,
                                         @ApiParam(required = false, name = "nickname", value = "微信用户昵称")@RequestParam(required = false) String nickname,
                                         @ApiParam(required = false, name = "sex", value = "微信用户性别")@RequestParam(required = false) String sex,
                                         @ApiParam(required = false, name = "headImgUrl", value = "微信用户头像URL")@RequestParam(required = false) String headImgUrl,
                                         @ApiParam(required = true, name = "fromOpenId", value = "推荐用户的openId")@RequestParam String fromOpenId) {
        Assert.notNull(openid);

        Result result = new Result();

        MemberDTO registerMemberDTO = null;

        //检查用户是否已经注册
        registerMemberDTO = memberService.checkMemberRegister(openid);
        if (registerMemberDTO != null) {

            //nothing to do
            result.setCode(ERROR_CODE);
            result.setMessage(ErrorCode.WECHAT_USER_EXIST.getErrorValue());

        } else { //新用户就创建一条新纪录

            registerMemberDTO = new MemberDTO();
            registerMemberDTO.setOpenId(openid);

            if (!StringUtils.isEmpty(nickname)) {
                registerMemberDTO.setNickname(nickname);
            }

            if (!StringUtils.isEmpty(sex)) {
                registerMemberDTO.setSex(sex);
            }

            if (!StringUtils.isEmpty(headImgUrl)) {
                registerMemberDTO.setHeaderUrl(headImgUrl);
            }


            //获取推荐用户的信息
            MemberDTO fromMemberDTO = memberService.checkMemberRegister(fromOpenId);


            //检查推荐用户是否为根用户
            long groupId = 0;
            String parentTree = null;
            if (StringUtils.isNotEmpty(fromMemberDTO.getParentOpenId())) {
                //nothing need to do
                //不是根用户就拿推荐用户的信息
                groupId = fromMemberDTO.getGroupId();

                //拿推荐用户组装
                parentTree = fromMemberDTO.getParentTree() + fromMemberDTO.getId() + TREE_SEPERETE;
            } else {
                //需要新建组
                GroupDTO groupDTO = new GroupDTO();

                groupDTO.setMemberCount(1l);
                groupDTO.setCoast(new BigDecimal(0));
                groupDTO.setCreateUser(openid);
                groupDTO.setStatus(1);
                groupDTO.setGroupLevel(Integer.parseInt(groupLevelService.findInitGroupLevel()));
                groupDTO.setCreateDate(new Date());

                groupService.create(groupDTO);


                parentTree = fromMemberDTO.getId() + TREE_SEPERETE;
                groupId = groupDTO.getId();
            }


            //保存
            registerMemberDTO.setRegisterDate(new Date());
            registerMemberDTO.setCreateDate(new Date());
            registerMemberDTO.setPriority(fromMemberDTO.getPriority() + 1);
            registerMemberDTO.setGroupId(groupId); //所在的组
            registerMemberDTO.setGroupName(fromMemberDTO.getGroupName()); //所在的组名
            registerMemberDTO.setParentId(fromMemberDTO.getId());  //所属上一级ID
            registerMemberDTO.setParentOpenId(fromOpenId); //所属上一级openID
            registerMemberDTO.setParentTree(parentTree);   //设置树结构
            registerMemberDTO.setMemberLevel(memberLevelService.findInitLevel());
            registerMemberDTO.setOwnerCoast(new BigDecimal(0));
            registerMemberDTO.setLeafCoast(new BigDecimal(0));
            //创建用户
            memberService.insert(registerMemberDTO);
        }


        result.setData(registerMemberDTO);

        return result;
    }

    /**
     * 微信入口
     * @param openid
     * @return
     */
    @PostMapping(value = "/income")
    @ApiOperation(value = "微信入口", notes = "微信入口并返回用户信息")
    public @ResponseBody
    Result checkMemberRegister(@ApiParam(required = true, name = "code", value = "微信返回code")@RequestParam String code,
    		@ApiParam(required = false, name = "fromOpenId", value = "推荐人openID")@RequestParam(required = false) String fromOpenId) {
    	log.info("fromOpenId:"+fromOpenId);
        Result result = new Result();
        JSONObject jsonObject = WeixinUtil.getOpenIdMap(code);
		if (null == jsonObject || StringUtils.isEmpty(jsonObject.getString("openid"))) {
			result.setCode(ERROR_CODE);
			result.setMessage("微信获取信息失败");
			return result;
		}
		String openid = jsonObject.getString("openid");
		log.info("openid:"+openid);
		MemberDTO registerMemberDTO = memberService.checkMemberRegister(openid);
		if (registerMemberDTO != null) {
            result.setCode(SUCCESS_CODE);
            result.setMessage(SUCCESS_MESSAGE);
            result.setData(registerMemberDTO);
            return result;
        }
		jsonObject = WeixinUtil.getUserInfoMap(jsonObject.getString("access_token"), openid);
		if (null == jsonObject || StringUtils.isEmpty(jsonObject.getString("openid"))) {
			result.setCode(ERROR_CODE);
			result.setMessage("微信拉取信息失败");
			return result;
		}
		String nickname = jsonObject.getString("nickname");
		String sex = jsonObject.getString("sex");
		String headImgUrl = jsonObject.getString("headimgurl");
	
        registerMemberDTO = new MemberDTO();
        registerMemberDTO.setOpenId(openid);
        if (!StringUtils.isEmpty(nickname)) {
            registerMemberDTO.setNickname(nickname);
            log.info("nickname:"+nickname);
        }
        if (!StringUtils.isEmpty(sex)) {
            registerMemberDTO.setSex(sex);
            log.info("sex:"+sex);
        }
        if (!StringUtils.isEmpty(headImgUrl)) {
            registerMemberDTO.setHeaderUrl(headImgUrl);
            log.info("headImgUrl:"+headImgUrl);
        }
        //获取推荐用户的信息
        
        MemberDTO fromMemberDTO = memberService.checkMemberRegister(fromOpenId);
        if (null == fromMemberDTO) {
        	result.setCode(ERROR_CODE);
			result.setMessage("推荐人不正确");
			return result;
        }
        //检查推荐用户是否为根用户
        long groupId = 0;
        String parentTree = null;
        if (StringUtils.isNotEmpty(fromMemberDTO.getParentOpenId())) {
            //nothing need to do
            //不是根用户就拿推荐用户的信息
            groupId = fromMemberDTO.getGroupId();

            //拿推荐用户组装
            parentTree = fromMemberDTO.getParentTree() + fromMemberDTO.getId() + TREE_SEPERETE;
        } else {
            //需要新建组
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setMemberCount(1l);
            groupDTO.setCoast(new BigDecimal(0));
            groupDTO.setCreateUser(openid);
            groupDTO.setStatus(1);
            groupDTO.setGroupLevel(Integer.parseInt(groupLevelService.findInitGroupLevel()));
            groupDTO.setCreateDate(new Date());
            groupService.create(groupDTO);
            parentTree = fromMemberDTO.getId() + TREE_SEPERETE;
            groupId = groupDTO.getId();
        }
        //保存
        registerMemberDTO.setRegisterDate(new Date());
        registerMemberDTO.setCreateDate(new Date());
        registerMemberDTO.setPriority(fromMemberDTO.getPriority() + 1);
        registerMemberDTO.setGroupId(groupId); //所在的组
        registerMemberDTO.setGroupName(fromMemberDTO.getGroupName()); //所在的组名
        registerMemberDTO.setParentId(fromMemberDTO.getId());  //所属上一级ID
        registerMemberDTO.setParentOpenId(fromOpenId); //所属上一级openID
        registerMemberDTO.setParentTree(parentTree);   //设置树结构
        registerMemberDTO.setMemberLevel(memberLevelService.findInitLevel());
        registerMemberDTO.setOwnerCoast(new BigDecimal(0));
        registerMemberDTO.setLeafCoast(new BigDecimal(0));
        //创建用户
        memberService.insert(registerMemberDTO);
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MESSAGE);
        result.setData(registerMemberDTO);
        return result;
    }

}
