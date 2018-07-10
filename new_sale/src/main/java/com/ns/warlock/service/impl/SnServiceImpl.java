package com.ns.warlock.service.impl;

import com.ns.warlock.dao.SnDao;
import com.ns.warlock.dto.SnDTO.Type;
import com.ns.warlock.service.SnService;
import com.ns.warlock.util.FreemarkerUtils;
import freemarker.template.TemplateException;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service("snServiceImpl")
public class SnServiceImpl implements SnService, InitializingBean {

    /**
     * 商品
     */
    private HiloOptimizer productHiloOptimizer;

    /**
     * 订单
     */
    private HiloOptimizer orderHiloOptimizer;

    /**
     * 快递
     */
    private HiloOptimizer shipHiloOptimizer;

    @Value("${sn.product.prefix}")
    private String productPrefix;
    @Value("${sn.product.maxLo}")
    private int productMaxLo;

    @Value("${sn.order.prefix}")
    private String orderPrefix;
    @Value("${sn.order.maxLo}")
    private int orderMaxLo;

    @Value("${sn.shipping.prefix}")
    private String shippingPrefix;
    @Value("${sn.shipping.maxLo}")
    private int shippingMaxLo;

    @Autowired
    private SnDao snDao;

    /**
     * 方法用于properties设置完以后初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        productHiloOptimizer = new HiloOptimizer(Type.product, productPrefix, productMaxLo);
        orderHiloOptimizer = new HiloOptimizer(Type.order, orderPrefix, orderMaxLo);
        shipHiloOptimizer = new HiloOptimizer(Type.shipping, shippingPrefix, shippingMaxLo);
    }

    @Override
    public String generate(Type type) {
        Assert.notNull(type);
        if (type == Type.product) {
            return productHiloOptimizer.generate();
        } else if (type == Type.order) {
            return orderHiloOptimizer.generate();
        } else if (type == Type.shipping) {
            return shipHiloOptimizer.generate();
        }
        return null;
    }


    /**
     * 高低位算法
     */
    private class HiloOptimizer {

        //类型
        private Type type;
        //前缀
        private String prefix;
        //最大低位
        private int maxLo;
        //开始数
        private int lo;
        //高位
        private long hi;
        //最后一个值
        private long lastValue;

        public HiloOptimizer(Type type, String prefix, int maxLo) {
            this.type = type;
            this.prefix = prefix != null ? prefix.replace("{", "${") : "";
            this.maxLo = maxLo;
            this.lo = maxLo + 1;
        }

        public synchronized String generate() {
            if (lo > maxLo) { //正常情况
                lastValue = snDao.getLastValue(type.ordinal());
                snDao.updateLastValue(lastValue + 1, type.ordinal());
                lo = lastValue == 0 ? 1 : 0;
                hi = lastValue * (maxLo + 1);
            }

            try {
                return FreemarkerUtils.process(prefix, null) + (hi + lo++);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            return String.valueOf(hi + lo++);
        }

    }
}
