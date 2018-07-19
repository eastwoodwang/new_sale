package com.ns.warlock.controller.wechat;


import com.ns.warlock.dto.WeixinTextMsg;
import com.ns.warlock.util.WeixinMessageUtil;
import com.ns.warlock.util.WeixinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController("wechatController")
@RequestMapping("/wechat")
@Api(value = "微信接口")
@CrossOrigin
public class WechatController extends BaseController {

	public static Logger log = LoggerFactory.getLogger(WechatController.class);

    /**
     * 微信入口
     * @param 
     * @return
     * @throws IOException 
     */
    @GetMapping(value = "/first")
    @ApiOperation(value = "微信入口", notes = "验证微信入口")
    public void doGetFirst (HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(WeixinUtil.checkSignature(signature, timestamp, nonce)){
        	response.getWriter().print(echostr);
        }
    }

    /**
     * 微信入口
     * @return
     * @throws UnsupportedEncodingException 
     */
    @PostMapping(value = "/first")
    @ApiOperation(value = "微信入口", notes = "获取微信信息")
    public void doPostFirst (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	try {
            Map<String,String> map = WeixinMessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");   
            String message = null;
            // 普通消息
            if (WeixinMessageUtil.MESSAGE_TEXT.equals(msgType)) { // 文本消息
                // 处理文本消息
            	message = new WeixinTextMsg("您发送的内容是:"+content, fromUserName, toUserName).getXml();
            } else if(WeixinMessageUtil.MESSAGE_EVENT.equals(msgType)){
            	//事件类型 event
                String eventType = map.get("Event");
                //关注 和 扫码-未关注时扫码 
                if(WeixinMessageUtil.EVENT_SUB.equals(eventType)){
                	String eventKey = map.get("EventKey");
                	if (StringUtils.isNotEmpty(eventKey) && eventKey.startsWith("qrscene_")) {
                		message = new WeixinTextMsg("欢迎扫码关注"+eventKey+","+eventKey.replace("qrscene_", ""), fromUserName, toUserName).getXml();
                	} else {
                        message = new WeixinTextMsg("欢迎关注"+eventKey, fromUserName, toUserName).getXml();
                	}
                } else if (WeixinMessageUtil.EVENT_UNSUB.equals(eventType)) {
                	//取消关注
                } else if (WeixinMessageUtil.EVENT_SCAN.equals(eventType)) {
                	//扫描带参数二维码-用户已关注时
                	
                } 
            } else if (WeixinMessageUtil.MESSAGE_IMAGE.equals(msgType)) { // 图片消息
                // todo 处理图片消息
            } else if (WeixinMessageUtil.MESSAGE_VOICE.equals(msgType)) { //语音消息
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

}
