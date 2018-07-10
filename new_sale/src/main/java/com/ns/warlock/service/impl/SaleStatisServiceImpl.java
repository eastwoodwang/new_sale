package com.ns.warlock.service.impl;


import com.ns.warlock.dao.SaleStatisDao;
import com.ns.warlock.dto.SaleStatisDTO;
import com.ns.warlock.service.SaleStatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("saleStatisServiceImpl")
public class SaleStatisServiceImpl implements SaleStatisService {

    @Autowired
    private SaleStatisDao saleStatisDao;

    @Override
    public List<SaleStatisDTO> findList(String memberId) {
        return saleStatisDao.findList(memberId);
    }

    @Override
    public List<SaleStatisDTO> leafList(String memberId) {
        return saleStatisDao.leafList(memberId);
    }

    @Override
    public void update(SaleStatisDTO saleStatisDTO) {
        saleStatisDao.update(saleStatisDTO);
    }

    @Override
    public void create(SaleStatisDTO saleStatisDTO) {
        saleStatisDao.insert(saleStatisDTO);
    }

    @Override
    public List<SaleStatisDTO> topMemberList(String month) {
        return saleStatisDao.topMemberList(month);
    }

    @Override
    public BigDecimal sumLeafList(long groupId, long memberId, String month) {
        Map param = new HashMap();
        param.put("groupId", groupId);
        param.put("memberId", memberId);
        param.put("month", month);
        return saleStatisDao.sumLeafList(param);
    }
}
