package com.ns.warlock.dto;

public class SnDTO extends BaseDTO {

    private static final long serialVersionUID = 2447280360488092933L;

    public enum Type {

        /** 商品 */
        product,

        /** 订单 */
        order,

        /** 发货单 */
        shipping

    }

    private Type type;

    private Long lastValue;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getLastValue() {
        return lastValue;
    }

    public void setLastValue(Long lastValue) {
        this.lastValue = lastValue;
    }
}
