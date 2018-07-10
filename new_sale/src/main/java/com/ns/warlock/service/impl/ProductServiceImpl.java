package com.ns.warlock.service.impl;

import com.ns.warlock.dao.ProductDao;
import com.ns.warlock.dto.ProductDTO;
import com.ns.warlock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductDTO> findAll() {
        return productDao.findAll(null);
    }

    @Override
    public List<ProductDTO> findMarketableAll() {
        return productDao.findAll("1");
    }

    @Override
    public ProductDTO find(long productId) {
        return productDao.find(productId);
    }

    @Override
    public long save(ProductDTO productDTO) {
        return productDao.save(productDTO);
    }

    @Override
    public void update(ProductDTO productDTO) {
        productDao.update(productDTO);
    }

    @Override
    public void delete(Long[] ids) {
        productDao.delete(ids);
    }

    @Override
    public void changeStock(Long productId, BigDecimal quantity) {
        Map params = new HashMap<>();
        params.put("productId", productId);
        params.put("quantity", quantity);
        productDao.changeStock(params);
    }

    @Override
    public void batchToggleMarketable(Long[] ids, String marketable) {
        Map map = new HashMap();
        map.put("ids", ids);
        map.put("marketable", marketable);
        productDao.batchToggleMarketable(map);
    }
}
