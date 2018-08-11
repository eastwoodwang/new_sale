package com.ns.warlock.controller.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.dto.MemberRecommendDTO;
import com.ns.warlock.dto.WeixinTextMsg;
import com.ns.warlock.service.MemberRecommendService;
import com.ns.warlock.service.MemberService;
import com.ns.warlock.util.WeixinMessageUtil;
import com.ns.warlock.util.WeixinUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("wechatController")
@RequestMapping("/wechat")
@Api(value = "微信接口")
@CrossOrigin
public class WechatController extends BaseController {
	
	public static String redicrtUrl = "http://www.baidu.com/";

	public static Logger log = LoggerFactory.getLogger(WechatController.class);

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "memberRecommendServiceImpl")
	private MemberRecommendService memberRecommendService;

	/**
	 * 微信入口
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/first")
	@ApiOperation(value = "微信入口", notes = "验证微信入口")
	public void doGetFirst(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if (WeixinUtil.checkSignature(signature, timestamp, nonce)) {
			response.getWriter().print(echostr);
		}
	}

	/**
	 * 微信入口
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping(value = "/first")
	@ApiOperation(value = "微信入口", notes = "获取微信信息")
	public void doPostFirst(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Map<String, String> map = WeixinMessageUtil.xmlToMap(request);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			// 普通消息
			if (WeixinMessageUtil.MESSAGE_TEXT.equals(msgType)) { // 文本消息
				// 处理文本消息
				message = new WeixinTextMsg("您发送的内容是:" + content, fromUserName, toUserName).getXml();
			} else if (WeixinMessageUtil.MESSAGE_EVENT.equals(msgType)) {
				// 事件类型 event
				String eventType = map.get("Event");
				// 关注 和 扫码-未关注时扫码
				if (WeixinMessageUtil.EVENT_SUB.equals(eventType)) {
					String msg = memberRecommend(fromUserName, map.get("EventKey"));
					message = new WeixinTextMsg("欢迎您关注我们,"+msg, fromUserName, toUserName).getXml();
				} else if (WeixinMessageUtil.EVENT_UNSUB.equals(eventType)) {
					// 取消关注
				} else if (WeixinMessageUtil.EVENT_SCAN.equals(eventType)) {
					// 扫描带参数二维码-用户已关注时
					message = new WeixinTextMsg(memberRecommend(fromUserName, map.get("EventKey")), fromUserName, toUserName).getXml();
				}
			} else if (WeixinMessageUtil.MESSAGE_IMAGE.equals(msgType)) { // 图片消息
				// todo 处理图片消息
			} else if (WeixinMessageUtil.MESSAGE_VOICE.equals(msgType)) { // 语音消息
				// todo 处理语音消息
			} else if (WeixinMessageUtil.MESSAGE_VIDEO.equals(msgType)) { // 视频消息
				// todo 处理视频消息
			} else if (WeixinMessageUtil.MESSAGE_SHORT_VIDEO.equals(msgType)) { // 小视频消息
				// todo 处理小视频消息
			} else if (WeixinMessageUtil.MESSAGE_LOCATION.equals(msgType)) { // 地理位置消息
				// todo 处理地理位置消息
			} else if (WeixinMessageUtil.MESSAGE_LINK.equals(msgType)) { // 链接消息
				// todo 处理链接消息
			}
			if (null != message) {
				response.getWriter().print(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().close();
		}
	}

	private String memberRecommend(String openid, String parentOpenid) {
		if(StringUtils.isEmpty(parentOpenid)) {
			return "";
		}
		MemberRecommendDTO memberRecommendDTO = memberRecommendService.find(openid);
		parentOpenid = parentOpenid.replace("qrscene_", "");
		MemberRecommendDTO recod = new MemberRecommendDTO();
		recod.setOpenId(openid);
		recod.setParentOpenId(parentOpenid);
		memberRecommendService.insert(recod);
		if (null != memberRecommendDTO) {
			MemberDTO parentMemberDTO = memberService.checkMemberRegister(memberRecommendDTO.getParentOpenId());
			return "对不起，您已经扫描了'"+parentMemberDTO.getNickname()+"'的二维码，<a href='"+redicrtUrl+"'>点击链接注册吧</a>";
		}
		MemberDTO parentMemberDTO = memberService.checkMemberRegister(parentOpenid);
		return "您已成功扫描'"+parentMemberDTO.getNickname()+"'的二维码，<a href='"+redicrtUrl+"'>点击链接注册吧</a>";
	}
}
