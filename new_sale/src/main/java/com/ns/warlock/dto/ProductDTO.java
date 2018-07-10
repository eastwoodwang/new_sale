package com.ns.warlock.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品
 */
public class ProductDTO extends BaseDTO {

    private static final long serialVersionUID = 7798747003684403039L;

    /**
     * 商品编号
     */
    private String sn;

    /**
     * 名字
     */
    private String name;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 会员价格
     */
    private BigDecimal memberPrice;

    /**
     * 展示图片
     */
    private String displayImage;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 已结分配的库存
     */
    private Integer allocateStock;

    /**
     * 上架状态
     */
    private String marketable; //1上架 2 下架

    /**
     * 商品图片
     */
    private List<ProductImageDTO> productImages;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getAllocateStock() {
        return allocateStock;
    }

    public void setAllocateStock(Integer allocateStock) {
        this.allocateStock = allocateStock;
    }

    public String getMarketable() {
        return marketable;
    }

    public void setMarketable(String marketable) {
        this.marketable = marketable;
    }

    public List<ProductImageDTO> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageDTO> productImages) {
        this.productImages = productImages;
    }
}
