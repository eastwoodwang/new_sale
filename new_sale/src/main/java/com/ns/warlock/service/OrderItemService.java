package com.ns.warlock.service;

import com.ns.warlock.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    /**
     * 获取当前用户当前订单子项
     * @return
     */
    List<OrderItemDTO> findList(String memberId, Long orderId);

    /**
     * 修改
     * @param orderSn
     * @param productSn
     * @param quantity
     */
    void update(String orderSn, String productSn, BigDecimal quantity);

    /**
     * 查询
     * @param id
     * @return
     */
    OrderItemDTO find(Long id);

    /**
     * 删除
     * @param ids
     */
    void cancel(Long[] ids);

    /**
     * 创建订单
     * @param orderItemDTO
     * @return
     */
    void create(OrderItemDTO orderItemDTO);

}
