package com.ns.warlock.dao;

import com.ns.warlock.dto.OrderDTO;

import java.util.List;
import java.util.Map;

/**
 * 订单
 */
public interface OrderDao {

    /**
     * 获取全部
     * @return
     */
    List<OrderDTO> findAll();

    /**
     * 根据用户ID查找
     * @param memberId
     * @return
     */
    List<OrderDTO> findByMemberId(long memberId);

    /**
     * 修改
     * @param orderDTO
     */
    void update(OrderDTO orderDTO);

    /**
     * 查询
     * @param id
     * @return
     */
    OrderDTO find(long id);

    /**
     * 删除
     * @param map
     */
    void cancel(Map map);

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    void create(OrderDTO orderDTO);

    /**
     * 检查订单是否都在改用户名下
     * @param map
     * @return
     */
    boolean checkOrderExist(Map map);
}
