package com.ns.warlock.dto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;

/**
 * 商品图片表
 */
public class ProductImageDTO extends BaseDTO implements Comparable<ProductImageDTO> {

    private static final long serialVersionUID = -6788376193598269032L;

    private String title;

    private String source;

    private String large;

    private String medium;

    private String thumbnail;

    private int imageOrder;

    private String productSn;

    private MultipartFile file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * 比对实现按照Order排序
     * @param productImageDTO
     * @return
     */
    @Override
    public int compareTo(ProductImageDTO productImageDTO) {
        return new CompareToBuilder().append(getImageOrder(), productImageDTO.getImageOrder()).toComparison();
    }

    @Transient
    public boolean isEmpty() {
        return (getFile() == null || getFile().isEmpty()) && (StringUtils.isEmpty(getSource()) || StringUtils.isEmpty(getLarge()) || StringUtils.isEmpty(getMedium()) || StringUtils.isEmpty(getThumbnail()));
    }
}
