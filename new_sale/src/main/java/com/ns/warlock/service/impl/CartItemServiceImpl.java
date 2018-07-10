package com.ns.warlock.service.impl;

import com.ns.warlock.dao.CartItemDao;
import com.ns.warlock.dto.CartItemDTO;
import com.ns.warlock.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cartItemServiceImpl")
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public List<CartItemDTO> findAll(long memberId) {
        return cartItemDao.findAll(memberId);
    }

    @Override
    public void insert(CartItemDTO cartItemDTO) {
        cartItemDao.insert(cartItemDTO);
    }

    @Override
    public void update(Integer quantity, long id) {
        cartItemDao.update(quantity, id);
    }

    @Async
    @Override
    public void delete(Long[] ids) {
        cartItemDao.delete(ids);
    }

    @Override
    public CartItemDTO checkProductExist(Map params) {
        return cartItemDao.checkProductExist(params);
    }

    @Override
    public void addQuantity(long id) {
        cartItemDao.addQuantity(id);
    }

    @Override
    public void subQuantity(long id) {
        cartItemDao.subQuantity(id);
    }

    @Override
    public List<CartItemDTO> findList(String memberId, Long[] cartItemIds) {
        Map params = new HashMap();
        params.put("memberId", memberId);
        params.put("ids", cartItemIds);
        return cartItemDao.findList(params);
    }
}
