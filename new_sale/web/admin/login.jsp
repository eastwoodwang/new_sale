<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="java.security.interfaces.RSAPublicKey"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.ns.warlock.util.SpringUtils"%>
<%@page import="com.ns.warlock.service.RSAService"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
String captchaId = UUID.randomUUID().toString();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
if (applicationContext != null) {
%>
<shiro:authenticated>
<%
response.sendRedirect(base + "/admin/common/main.jhtml");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%
if (applicationContext != null) {
	RSAService rsaService = SpringUtils.getBean("rsaServiceImpl", RSAService.class);
	RSAPublicKey publicKey = rsaService.generateKey(request);
	String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());
	String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());
	
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
			message = "admin.login.disabledAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
			message = "admin.login.lockedAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
			message = "admin.login.accountLockCount";
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%> - Powered By FreeWinds</title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="FreeWinds" />
<meta name="copyright" content="FreeWinds" />
<link rel="icon" href="<%=base%>/resources/login/images/favicon.ico" type="image/x-icon" />
<link href="<%=base%>/resources/login/css/login.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/login/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/admin/css/plugins/toastr/toastr.min.css" rel="stylesheet">


<script type="text/javascript" src="<%=base%>/resources/login/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=base%>/resources/login/js/jsbn.js"></script>
<script type="text/javascript" src="<%=base%>/resources/login/js/prng4.js"></script>
<script type="text/javascript" src="<%=base%>/resources/login/js/rng.js"></script>
<script type="text/javascript" src="<%=base%>/resources/login/js/rsa.js"></script>
<script type="text/javascript" src="<%=base%>/resources/login/js/base64.js"></script>
<!-- Toastr -->
<script type="text/javascript" src="<%=base%>/resources/admin/js/plugins/toastr/toastr.min.js"></script>

<script type="text/javascript">
	$().ready( function() {
		
		var $loginForm = $("#loginForm");
		var $enPassword = $("#enPassword");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		
		// 更换验证码
		$("a.change").click( function() {
			$captchaImage.attr("src", "<%=base%>/admin/common/captcha.jhtml?captchaId=<%=captchaId%>&timestamp=" + (new Date()).valueOf());
		});

        //自定义
        toastr.options = {
            positionClass: "toast-top-center"
        }
		
		// 表单验证
		$loginForm.submit( function() {
			
			if ($username.val() == "") {
                toastr.warning('<%=SpringUtils.getMessage("admin.login.usernameRequired")%>');
				//$.message("warn", "<%=SpringUtils.getMessage("admin.login.usernameRequired")%>");
				return false;
			}
			if ($password.val() == "") {
                toastr.warning('<%=SpringUtils.getMessage("admin.login.passwordRequired")%>');
				//$.message("warn", "<%=SpringUtils.getMessage("admin.login.passwordRequired")%>");
				return false;
			}
			if ($captcha.val() == "") {
                toastr.warning('<%=SpringUtils.getMessage("admin.login.captchaRequired")%>');
				//$.message("warn", "<%=SpringUtils.getMessage("admin.login.captchaRequired")%>");
				return false;
			}			
			
			var rsaKey = new RSAKey();
			rsaKey.setPublic(b64tohex("<%=modulus%>"), b64tohex("<%=exponent%>"));
			var enPassword = hex2b64(rsaKey.encrypt($password.val()));
			$enPassword.val(enPassword);
			
		});

        <%if (message != null) {%>
          //$.message("error", "<%=SpringUtils.getMessage(message)%>");
          toastr.warning('<%=SpringUtils.getMessage(message)%>');
        <%}%>
    });
</script>
<%}%>
</head>
<body>
	<%if (applicationContext != null) {%>
	  <div class="bg-dot"></div>
	  <div class="login-layout">
	     <div class="top">
	        <h5><%=SpringUtils.getMessage("admin.login.title_01")%></h5>
	        <h2><%=SpringUtils.getMessage("admin.login.title_02")%></h2>
	        <h6><%=SpringUtils.getMessage("admin.login.title_03")%></h6>
	     </div>
	     <div class="box">
	        <form id="loginForm" action="login.jsp" method="post">
				<input type="hidden" id="enPassword" name="enPassword" />
				<input type="hidden" name="captchaId" value="<%=captchaId%>" />

	            <span>
	                <label><%=SpringUtils.getMessage("admin.login.username")%></label>
	                <input id="username" name="username" type="text" class="input-text" required/>
	            </span>
	            
	            <span>
	                <label><%=SpringUtils.getMessage("admin.login.password")%></label>
	                <input id="password" type="password" class="input-password" autocomplete="off" required/>
	            </span>

	            <span>
	                <div class="code">
	                   <div class="arrow"></div>
	                   <div class="code-img">
	                      <img id="captchaImage" class="captchaImage" src="<%=base%>/admin/common/captcha.jhtml?captchaId=<%=captchaId%>" />
	                   </div>
	                   <a id="hide" class="close" title="<%=SpringUtils.getMessage("admin.login.closeCaptcha")%>">
        	             <i></i>
                       </a>
                       <a class="change" title="<%=SpringUtils.getMessage("admin.login.changeCaptcha")%>">
        	             <i></i>
                       </a>
	                </div>
	                <input name="captcha" type="text" class="input-code" id="captcha" placeholder="<%=SpringUtils.getMessage("admin.login.captcha.name")%>" maxlength="4" autocomplete="off" required/>
	            </span>
	            
	            <span>
	                <input type="submit" class="input-button" value="<%=SpringUtils.getMessage("admin.login.login")%>" />
	            </span>           
	        </form>
	     </div>
	  </div>
	  <div class="bottom">
		  <h5><%=SpringUtils.getMessage("admin.login.title_04")%></h5>
		  <h6><%=SpringUtils.getMessage("admin.login.title_05")%></h6>
	  </div>	  
	<%}%>
	
	<script type="text/javascript">
	   $(document).ready(function(){
		   
			var $code = $(".code");
			var $hidden = $("a#hide");
			var $captcha = $("#captcha");
			
			var random_bg = Math.floor(Math.random()*2+1);
			var bg='url(../resources/login/images/bg_'+random_bg+'.jpg)';
			$("body").css("background-image", bg);
			
			//Get the captcha
			$hidden.click(function(){
				$code.fadeOut("slow");
			});
			
			$captcha.focus(function(){
				$code.fadeIn("fast");
		    });

	   });
	</script>
</body>
</html>