package com.ns.warlock.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *
 */
public class LoginIdPasswordCode extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    private String code;//验证码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginIdPasswordCode() {
        super();
    }

    public LoginIdPasswordCode(String username, char[] password, String host) {
        super(username, password, host);
    }

    public LoginIdPasswordCode(String username, char[] password,
                                boolean rememberMe, String host, String code) {
        super(username, password, rememberMe, host);
        this.code = code;
    }
}
