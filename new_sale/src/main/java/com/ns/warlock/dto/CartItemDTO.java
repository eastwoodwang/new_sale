package com.ns.warlock.dto;

import java.math.BigDecimal;

/**
 * 购物车内购物项目
 */
public class CartItemDTO extends BaseDTO {

    /**
     * 购物车ID
     */
    private long cartId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品数量
     */
    private BigDecimal quantity;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品展示页
     */
    private String displayImage;

    /**
     * 是否上架
     */
    private String marketable;

    /**
     * 价钱
     */
    private BigDecimal price;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public String getMarketable() {
        return marketable;
    }

    public void setMarketable(String marketable) {
        this.marketable = marketable;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
