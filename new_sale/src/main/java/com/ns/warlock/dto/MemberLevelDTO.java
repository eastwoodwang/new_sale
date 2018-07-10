package com.ns.warlock.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员等级DTO
 */
public class MemberLevelDTO implements Serializable {

    private static final long serialVersionUID = -2245606939432042493L;

    private long id;

    private String levelName;

    private BigDecimal minRange;

    private BigDecimal maxRange;

    private int priority;

    private int system;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }
}
