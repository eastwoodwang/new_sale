package com.ns.warlock.dao;


import com.ns.warlock.dto.OrderItemDTO;

import java.util.List;
import java.util.Map;

public interface OrderItemDao {

    /**
     * 获取订单下全部子项
     * @param map
     * @return
     */
    List<OrderItemDTO> findList(Map map);

//    /**
//     * 修改
//     * @param
//     */
//    void update(Map params);
//
//    /**
//     * 查询
//     * @param id
//     * @return
//     */
//    OrderItemDTO find(Long id);

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
