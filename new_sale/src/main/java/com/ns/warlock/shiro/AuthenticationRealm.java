package com.ns.warlock.shiro;


import com.ns.warlock.Principal;
import com.ns.warlock.dto.AdminDTO;
import com.ns.warlock.service.AdminService;
import com.ns.warlock.service.CaptchaService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 权限认证
 */
public class AuthenticationRealm extends AuthorizingRealm {

    //@Resource(name = "sysUserServiceImpl")
    //private SysUserService sysUserService;

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;
    @Resource(name = "captchaServiceImpl")
    private CaptchaService captchaService;

    /**
     * 获取认证信息
     * @param token
     *           令牌
     * @return 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) throws AuthenticationException {

        AuthenticationToken authenticationToken = (AuthenticationToken) token;
        String username = authenticationToken.getUsername();
        String password = new String(authenticationToken.getPassword());
        String captchaId = authenticationToken.getCaptchaId();
        String captcha = authenticationToken.getCaptcha();
        String ip = authenticationToken.getHost();

        //校验验证码
        if (!captchaService.isValid(captchaId, captcha)) {
            throw new UnsupportedTokenException();
        }

        if (username != null && !password.equals("") && !password.equals("null")) {

            AdminDTO adminDTO = adminService.findByUsername(username);
            if (adminDTO == null) {
                throw new UnknownAccountException();
            }

            if (!DigestUtils.md5Hex(password).equals(adminDTO.getPassword())) {
                throw new IncorrectCredentialsException();
            }
            return new SimpleAuthenticationInfo(new Principal(adminDTO.getId(), username, adminDTO.getName()), password, getName());
        }
        throw new UnknownAccountException();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
        if (principal != null) {
            Set<String> authorities = adminService.findPermissions(principal.getUsername());
            if (authorities != null) {
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                authorizationInfo.addRoles(adminService.findRoles(principal.getUsername()));
                authorizationInfo.addStringPermissions(authorities);
                return authorizationInfo;
            }
        }
        return null;
    }
}
