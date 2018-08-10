package com.ns.warlock.dto;

public class MemberRecommendDTO extends BaseDTO {

	private static final long serialVersionUID = 248063867791743068L;

	/**
     * 微信的OPENID
     */
    private String openId;

    /**
     * 推荐人OPENID
     */
    private String parentOpenId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getParentOpenId() {
        return parentOpenId;
    }

    public void setParentOpenId(String parentOpenId) {
        this.parentOpenId = parentOpenId;
    }
}
