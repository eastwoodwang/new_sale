package com.ns.warlock.dto;


/**
 * 购物车
 */
public class CartDTO extends BaseDTO {

    /**
     * 用户ID
     */
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
