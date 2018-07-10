package com.ns.warlock.common;

public class Message {

    /**
     * 类型
     */
    public enum Type {

        /** 成功 */
        success,

        /** 警告 */
        warn,

        /** 错误 */
        error
    }

    /** 类型 */
    private Type type;

    /** 内容 */
    private String content;

    /**
     * 初始化一个新创建的 Message 对象，使其表示一个空消息。
     */
    public Message() {

    }

    /**
     * 初始化一个新创建的 Message 对象
     *
     * @param type
     *            类型
     */
    public Message(Type type) {
        this(type, null);
    }

    /**
     * 初始化一个新创建的 Message 对象
     *
     * @param type
     *            类型
     * @param content
     *            内容
     */
    public Message(Type type, String content) {
        this.type = type;
        this.content = content;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
