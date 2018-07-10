package com.ns.warlock.service.impl;

import com.ns.warlock.dao.CartDao;
import com.ns.warlock.dto.CartDTO;
import com.ns.warlock.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cartServiceImpl")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public void insert(CartDTO cartDTO) {
        cartDao.insert(cartDTO);
    }

    @Override
    public CartDTO find(String memberId) {
        return cartDao.find(memberId);
    }
}
