package com.ns.warlock.common;

/**
 * 返回对象
 * @param <T>
 */
public class Result<T> {

    /**
     * 返回编码
     */
    private String code; //0:成功 1:失败

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
