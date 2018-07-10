package com.ns.warlock.service.impl;

import com.ns.warlock.dao.OrderItemDao;
import com.ns.warlock.dto.OrderItemDTO;
import com.ns.warlock.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderItemServiceImpl")
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<OrderItemDTO> findList(String memberId, Long orderId) {
        Map params = new HashMap();
        params.put("memberId", memberId);
        params.put("orderId", orderId);
        return orderItemDao.findList(params);
    }



    @Override
    public OrderItemDTO find(Long id) {
        return null;
    }

    @Override
    public void cancel(Long[] ids) {

    }

    @Override
    public void update(String orderSn, String productSn, BigDecimal quantity) {
        Map params = new HashMap<>();
        params.put("orderSn", orderSn);
        params.put("productSn", productSn);
        params.put("quantity", quantity);
        //orderItemDao.update(params);
    }

    @Override
    public void create(OrderItemDTO orderItemDTO) {
        orderItemDao.create(orderItemDTO);
    }
}
