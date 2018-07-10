package com.ns.warlock.common;

import java.io.Serializable;

public enum ErrorCode implements Serializable {

    DUPLICATE_ERROR(ErrorType.WECHAT, "用户已经注册"),
    PARAMETER_REQUIRE(ErrorType.WECHAT, "未找到任何信息"),
    ORDER_ITEM_REQUIRED(ErrorType.WECHAT, "订单内容不能为空"),
    ORDER_ID_REQUIRED(ErrorType.WECHAT, "订单号不能为空"),
    WECHAT_USER_EXIST(ErrorType.WECHAT, "该用户已经注册"),
    SYSTEM_MAINTAIN(ErrorType.WECHAT, "系统维护期无法提供服务");


    private ErrorType errorType;

    private String errorValue;

    ErrorCode(ErrorType errorType, String errorValue){
        this.errorType = errorType;
        this.errorValue = errorValue;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(String errorValue) {
        this.errorValue = errorValue;
    }
}
