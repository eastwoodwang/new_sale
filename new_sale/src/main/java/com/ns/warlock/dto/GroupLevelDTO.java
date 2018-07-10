package com.ns.warlock.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 团队等级DTO
 */
public class GroupLevelDTO implements Serializable {

    private static final long serialVersionUID = 3283682803378281206L;

    private long id;

    private String groupLevelName;

    private BigDecimal minRange;

    private BigDecimal maxRange;

    private BigDecimal discountRate;

    private int priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupLevelName() {
        return groupLevelName;
    }

    public void setGroupLevelName(String groupLevelName) {
        this.groupLevelName = groupLevelName;
    }

    public BigDecimal getMinRange() {
        return minRange;
    }

    public void setMinRange(BigDecimal minRange) {
        this.minRange = minRange;
    }

    public BigDecimal getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(BigDecimal maxRange) {
        this.maxRange = maxRange;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
