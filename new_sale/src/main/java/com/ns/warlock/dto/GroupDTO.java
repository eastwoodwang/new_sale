package com.ns.warlock.dto;

import java.math.BigDecimal;

/**
 * 用户组
 */
public class GroupDTO extends BaseDTO {

    private static final long serialVersionUID = 3792059751483795237L;

    private long memberCount;

    private String createUser;

    private String createUserName;

    private long orderAmount;

    private BigDecimal coast;

    private int status;

    private int groupLevel;

    private String groupLevelName;


    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getCoast() {
        return coast;
    }

    public void setCoast(BigDecimal coast) {
        this.coast = coast;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getGroupLevelName() {
        return groupLevelName;
    }

    public void setGroupLevelName(String groupLevelName) {
        this.groupLevelName = groupLevelName;
    }
}
