package com.ns.warlock.service.impl;

import com.ns.warlock.dao.OrderDao;
import com.ns.warlock.dao.OrderItemDao;
import com.ns.warlock.dto.OrderDTO;
import com.ns.warlock.dto.OrderItemDTO;
import com.ns.warlock.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<OrderDTO> findAll() {
        return orderDao.findAll();
    }

    @Transactional
    @Override
    public List<OrderDTO> findByMemberId(long memberId) {
        //获取订单项
        List<OrderDTO> orderDTOList = orderDao.findByMemberId(memberId);
        //获取每一个订单项的子项
        orderDTOList.stream().forEach( orderDTO -> {
            Map param = new HashMap();
            param.put("memberId", memberId);
            param.put("orderId", orderDTO.getId());
            List<OrderItemDTO> orderItemDTOList = orderItemDao.findList(param);
            orderDTO.setOrderItemDTOList(orderItemDTOList);
        });
        return orderDTOList;
    }

    @Override
    public void update(OrderDTO orderDTO) {
        orderDao.update(orderDTO);
    }

    @Override
    public OrderDTO find(long id) {
        return orderDao.find(id);
    }

    @Override
    public void cancel(String memberId, Long[] ids) {
        Map param = new HashMap<>();
        param.put("memberId", memberId);
        param.put("ids", ids);
        orderDao.cancel(param);
    }

    @Override
    public void create(OrderDTO orderDTO) {
        orderDao.create(orderDTO);
    }

    @Override
    public boolean checkOrderExist(String memberId, String[] orderIds) {
        Map param = new HashMap();
        param.put("memberId", memberId);
        param.put("orderIds", orderIds);

        //orderDao.checkOrderExist()

        return false;
    }
}
