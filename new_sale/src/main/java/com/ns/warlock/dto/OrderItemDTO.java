package com.ns.warlock.dto;

import java.math.BigDecimal;

/**
 * 订单项
 */
public class OrderItemDTO extends BaseDTO {

    private static final long serialVersionUID = -2548642797893416882L;

    /**
     * 订单ID
     */
    private long orderId;

    /**
     * 商品编号
     */
    private String productSn;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 交易价格
     */
    private BigDecimal orderPrice;

    /**
     * 商品数量
     */
    private BigDecimal quantity;

    /**
     * 商品缩略图
     */
    private String thumbnail;

    /**
     * 该项的总金额 price*quantity
     */
    private BigDecimal itemTotalPrice;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BigDecimal getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(BigDecimal itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
