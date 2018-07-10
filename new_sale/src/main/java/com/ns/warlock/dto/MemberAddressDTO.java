package com.ns.warlock.dto;

import io.swagger.annotations.ApiParam;

/**
 * 用户快递地址
 */
public class MemberAddressDTO extends BaseDTO {

    /** 地址 */
    @ApiParam(required = true, name = "address", value = "地址")
    private String address;

    /** 邮编 */
    @ApiParam(required = false, name = "zipCode", value = "邮编")
    private String zipCode;

    /** 电话 */
    @ApiParam(required = true, name = "phone", value = "电话")
    private String phone;

    /** 收货人姓名 */
    @ApiParam(required = true, name = "name", value = "收货人姓名")
    private String name;

    /** 用户ID */
    @ApiParam(required = true, name = "memberId", value = "用户id")
    private long memberId;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}
