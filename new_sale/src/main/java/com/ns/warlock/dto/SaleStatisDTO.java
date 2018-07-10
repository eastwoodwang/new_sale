package com.ns.warlock.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售统计DTO
 */
public class SaleStatisDTO extends BaseDTO {

    /** 会员ID */
    private long memberId;

    /** 销售月份 */
    private String month;

    /** 组ID */
    private long groupId;

    /** 返点率 */
    private BigDecimal rate;

    /** 个人销量 */
    private BigDecimal personSale;

    /** 个人下属销量 */
    private BigDecimal leafSale;

    /** 团队销量 */
    private BigDecimal groupSale;

    /** 个人销售所得 */
    private BigDecimal saleGain;

    /** 叶子节点销售总和所得 */
    private BigDecimal leafSaleGain;

    /** 父用户id */
    private long parentMemberId;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getPersonSale() {
        return personSale;
    }

    public void setPersonSale(BigDecimal personSale) {
        this.personSale = personSale;
    }

    public BigDecimal getLeafSale() {
        return leafSale;
    }

    public void setLeafSale(BigDecimal leafSale) {
        this.leafSale = leafSale;
    }

    public BigDecimal getGroupSale() {
        return groupSale;
    }

    public void setGroupSale(BigDecimal groupSale) {
        this.groupSale = groupSale;
    }

    public BigDecimal getSaleGain() {
        return saleGain;
    }

    public void setSaleGain(BigDecimal saleGain) {
        this.saleGain = saleGain;
    }

    public long getParentMemberId() {
        return parentMemberId;
    }

    public void setParentMemberId(long parentMemberId) {
        this.parentMemberId = parentMemberId;
    }

    public BigDecimal getLeafSaleGain() {
        return leafSaleGain;
    }

    public void setLeafSaleGain(BigDecimal leafSaleGain) {
        this.leafSaleGain = leafSaleGain;
    }

}
