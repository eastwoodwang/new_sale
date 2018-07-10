package com.ns.warlock;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class Setting implements Serializable {

    /**
     * 水印位置
     */
    public enum WatermarkPosition {

        /** 无 */
        no,

        /** 左上 */
        topLeft,

        /** 右上 */
        topRight,

        /** 居中 */
        center,

        /** 左下 */
        bottomLeft,

        /** 右下 */
        bottomRight
    }

    /**
     * 小数位精确方式
     */
    public enum RoundType {

        /** 四舍五入 */
        roundHalfUp,

        /** 向上取整 */
        roundUp,

        /** 向下取整 */
        roundDown
    }

    /** 缓存名称 */
    public static final String CACHE_NAME = "setting";

    /** 缓存Key */
    public static final Integer CACHE_KEY = 0;

    /** 分隔符 */
    private static final String SEPARATOR = ",";

    /** 网站名称 */
    private String siteName;

    /** 网站网址 */
    private String siteUrl;

    /** 商品图片(大)宽度 */
    private Integer largeProductImageWidth;

    /** 商品图片(大)高度 */
    private Integer largeProductImageHeight;

    /** 商品图片(中)宽度 */
    private Integer mediumProductImageWidth;

    /** 商品图片(中)高度 */
    private Integer mediumProductImageHeight;

    /** 商品缩略图宽度 */
    private Integer thumbnailProductImageWidth;

    /** 商品缩略图高度 */
    private Integer thumbnailProductImageHeight;

    /** 默认商品图片(大) */
    private String defaultLargeProductImage;

    /** 默认商品图片(小) */
    private String defaultMediumProductImage;

    /** 默认缩略图 */
    private String defaultThumbnailProductImage;

    /** 水印透明度 */
    private Integer watermarkAlpha;

    /** 水印图片 */
    private String watermarkImage;

    /** 水印位置 */
    private WatermarkPosition watermarkPosition;

    /** 价格精确位数 */
    private Integer priceScale;

    /** 价格精确方式 */
    private RoundType priceRoundType;

    /** 图片上传路径 */
    private String imageUploadPath;

    /** 允许上传图片扩展名 */
    private String uploadImageExtension;

    /** 上传文件最大限制 */
    private Integer uploadMaxSize;

    /** 首页标题图片路径 */
    private String titleImagePath;

    /** 首页活动路径 */
    private String promptImagePath;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Integer getLargeProductImageWidth() {
        return largeProductImageWidth;
    }

    public void setLargeProductImageWidth(Integer largeProductImageWidth) {
        this.largeProductImageWidth = largeProductImageWidth;
    }

    public Integer getLargeProductImageHeight() {
        return largeProductImageHeight;
    }

    public void setLargeProductImageHeight(Integer largeProductImageHeight) {
        this.largeProductImageHeight = largeProductImageHeight;
    }

    public Integer getMediumProductImageWidth() {
        return mediumProductImageWidth;
    }

    public void setMediumProductImageWidth(Integer mediumProductImageWidth) {
        this.mediumProductImageWidth = mediumProductImageWidth;
    }

    public Integer getMediumProductImageHeight() {
        return mediumProductImageHeight;
    }

    public void setMediumProductImageHeight(Integer mediumProductImageHeight) {
        this.mediumProductImageHeight = mediumProductImageHeight;
    }

    public Integer getThumbnailProductImageWidth() {
        return thumbnailProductImageWidth;
    }

    public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
        this.thumbnailProductImageWidth = thumbnailProductImageWidth;
    }

    public Integer getThumbnailProductImageHeight() {
        return thumbnailProductImageHeight;
    }

    public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
        this.thumbnailProductImageHeight = thumbnailProductImageHeight;
    }

    public String getDefaultLargeProductImage() {
        return defaultLargeProductImage;
    }

    public void setDefaultLargeProductImage(String defaultLargeProductImage) {
        this.defaultLargeProductImage = defaultLargeProductImage;
    }

    public String getDefaultMediumProductImage() {
        return defaultMediumProductImage;
    }

    public void setDefaultMediumProductImage(String defaultMediumProductImage) {
        this.defaultMediumProductImage = defaultMediumProductImage;
    }

    public String getDefaultThumbnailProductImage() {
        return defaultThumbnailProductImage;
    }

    public void setDefaultThumbnailProductImage(String defaultThumbnailProductImage) {
        this.defaultThumbnailProductImage = defaultThumbnailProductImage;
    }

    public Integer getWatermarkAlpha() {
        return watermarkAlpha;
    }

    public void setWatermarkAlpha(Integer watermarkAlpha) {
        this.watermarkAlpha = watermarkAlpha;
    }

    public String getWatermarkImage() {
        return watermarkImage;
    }

    public void setWatermarkImage(String watermarkImage) {
        this.watermarkImage = watermarkImage;
    }

    public WatermarkPosition getWatermarkPosition() {
        return watermarkPosition;
    }

    public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
        this.watermarkPosition = watermarkPosition;
    }

    public Integer getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(Integer priceScale) {
        this.priceScale = priceScale;
    }

    public RoundType getPriceRoundType() {
        return priceRoundType;
    }

    public void setPriceRoundType(RoundType priceRoundType) {
        this.priceRoundType = priceRoundType;
    }

    public String getUploadImageExtension() {
        return uploadImageExtension;
    }

    /**
     * 设置允许上传图片扩展名
     *
     * @param uploadImageExtension
     *            允许上传图片扩展名
     */
    public void setUploadImageExtension(String uploadImageExtension) {
        if (uploadImageExtension != null) {
            uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
        }
        this.uploadImageExtension = uploadImageExtension;
    }

    /**
     * 获取允许上传图片扩展名
     *
     * @return 允许上传图片扩展名
     */
    public String[] getUploadImageExtensions() {
        return StringUtils.split(uploadImageExtension, SEPARATOR);
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public void setTitleImagePath(String titleImagePath) {
        this.titleImagePath = titleImagePath;
    }

    public String getPromptImagePath() {
        return promptImagePath;
    }

    public void setPromptImagePath(String promptImagePath) {
        this.promptImagePath = promptImagePath;
    }

    /**
     * 获取上传文件最大限制
     *
     * @return 上传文件最大限制
     */
    public Integer getUploadMaxSize() {
        return uploadMaxSize;
    }

    /**
     * 设置上传文件最大限制
     *
     * @param uploadMaxSize
     *            上传文件最大限制
     */
    public void setUploadMaxSize(Integer uploadMaxSize) {
        this.uploadMaxSize = uploadMaxSize;
    }

    public String getImageUploadPath() {
        return imageUploadPath;
    }

    /**
     * 设置图片上传路径
     *
     * @param imageUploadPath
     *            图片上传路径
     */
    public void setImageUploadPath(String imageUploadPath) {
        if (imageUploadPath != null) {
            if (!imageUploadPath.startsWith("/")) {
                imageUploadPath = "/" + imageUploadPath;
            }
            if (!imageUploadPath.endsWith("/")) {
                imageUploadPath += "/";
            }
        }
        this.imageUploadPath = imageUploadPath;
    }
}
