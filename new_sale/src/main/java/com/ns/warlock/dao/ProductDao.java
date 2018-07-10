package com.ns.warlock.dao;

import com.ns.warlock.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductDao {

    /**
     * 获取所有商品列表
     * @return
     */
    List<ProductDTO> findAll(String marketable);

    /**
     * 获取某一个商品
     * @param productId
     * @return
     */
    ProductDTO find(long productId);

    /**
     * 保存 商品并返回ID
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
    void changeStock(Map map);

    /**
     * 批量上下架
     * @param map
     */
    void batchToggleMarketable(Map map);
}
