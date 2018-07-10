package com.ns.warlock.service;

import com.ns.warlock.dto.CartDTO;

import java.util.List;
import java.util.Map;

public interface CartService {

    /**
     * 添加商品至购物车
     * @param cartDTO
     */
    void insert(CartDTO cartDTO);

    /**
     * 检查用户有无此商品
     * @param memberId
     * @return
     */
    CartDTO find(String memberId);
}
