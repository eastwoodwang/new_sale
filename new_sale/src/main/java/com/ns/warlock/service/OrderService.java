package com.ns.warlock.service;

import com.ns.warlock.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    /**
     * 获取全部
     * @return
     */
    List<OrderDTO> findAll();

    /**
     * 获取用户订单
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
     * @param memberId
     * @param ids
     */
    void cancel(String memberId, Long[] ids);

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    void create(OrderDTO orderDTO);

    /**
     * 检查订单是否都在改用户名下
     * @param memberId
     * @param orderIds
     * @return
     */
    boolean checkOrderExist(String memberId, String[] orderIds);
}
