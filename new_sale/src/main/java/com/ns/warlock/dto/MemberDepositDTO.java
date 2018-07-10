package com.ns.warlock.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预充值DTO
 */
public class MemberDepositDTO extends BaseDTO {

    /** 会员ID */
    private long memberId;

    /** 会员名称 */
    private String memberName;

    /** 会员手机号 */
    private String memberPhone;

    /** 充值时间 */
    private Date date;

    /** 充值金额 */
    private BigDecimal credit;

    /** 操作员 */
    private String operator;

    /** 备注 */
    private String memo;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
