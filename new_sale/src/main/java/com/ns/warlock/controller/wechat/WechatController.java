package com.ns.warlock.controller.wechat;


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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     */
    @PostMapping(value = "/first")
    @ApiOperation(value = "微信入口", notes = "获取微信信息")
    public void doPostFirst (HttpServletRequest request, HttpServletResponse response) {
    	
    }

}
