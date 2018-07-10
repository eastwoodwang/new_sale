package com.ns.warlock.dao;

import com.ns.warlock.dto.CartItemDTO;

import java.util.List;
import java.util.Map;

public interface CartItemDao {


    /**
     * 获取所有购物车内项目
     * @param memberId
     * @return
     */
    List<CartItemDTO> findAll(long memberId);

    /**
     * 根据购物车子项ID获取集合
     * @param map
     * @return
     */
    List<CartItemDTO> findList(Map map);

    /**
     * 持久化
     * @param cartItemDTO
     */
    void insert(CartItemDTO cartItemDTO);

    /**
     * 修改购物车商品数量
     * @param id
     * @param quantity
     */
    void update(Integer quantity, long id);

    /**
     * 删除购物车某项
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 检查商品是否存在购物车
     * @return
     */
    CartItemDTO checkProductExist(Map params);

    /**
     * 新增一条记录
     * @param id
     */
    void addQuantity(long id);

    /**
     * 减少商品数
     * @param id
     */
    void subQuantity(long id);

}
