package com.ns.warlock.common;

import java.io.Serializable;

public enum ErrorType implements Serializable {

    WECHAT("前端错误"),

    ADMIN("后台错误");

    private String name;

    ErrorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
