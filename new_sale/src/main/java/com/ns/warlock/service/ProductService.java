package com.ns.warlock.service;

import com.ns.warlock.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    /**
     * 获取所有商品列表
     * @return
     */
    List<ProductDTO> findAll();

    /**
     * 获取所有上架商品列表
     * @return
     */
    List<ProductDTO> findMarketableAll();

    /**
     * 获取某一个商品
     * @param productId
     * @return
     */
    ProductDTO find(long productId);

    /**
     * 保存并回传productId
     * @param productDTO
     */
    long save(ProductDTO productDTO);

    /**
     * 修改
     * @param productDTO
     */
    void update(ProductDTO productDTO);

    /**
     * 删除
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 修改库存
     */
    void changeStock(Long productId, BigDecimal quantity);

    /**
     * 批量上下架
     * @param ids
     * @param marketable
     */
    void batchToggleMarketable(Long[] ids, String marketable);

}
