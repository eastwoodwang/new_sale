package com.ns.warlock.service;

import com.ns.warlock.dto.ProductImageDTO;

import java.util.List;

public interface ProductImageService {

    /**
     * 根据产品ID获取所有的对应图片
     * @param productSn
     * @return
     */
    List<ProductImageDTO> findByProductSn(String productSn);

    /**
     * 插入图片
     * @param productImageDTO
     */
    void insert(ProductImageDTO productImageDTO);

    /**
     * 修改图片
     * @param productImageDTO
     */
    void update(ProductImageDTO productImageDTO);

    /**
     * 生成商品图片
     *
     * @param productImageDTO
     *            商品图片
     */
    void build(ProductImageDTO productImageDTO);
}
