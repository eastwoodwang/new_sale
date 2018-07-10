package com.ns.warlock.service.impl;

import com.ns.warlock.dao.MemberLevelDao;
import com.ns.warlock.dto.MemberLevelDTO;
import com.ns.warlock.service.MemberLevelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("memberLevelServiceImpl")
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public List<MemberLevelDTO> findAll() {
        return memberLevelDao.findAll();
    }

    @Override
    public MemberLevelDTO find(Long id) {
        return memberLevelDao.find(id);
    }

    @Override
    public String selectPromptLevel(BigDecimal orderPay) {
        return memberLevelDao.selectPromptLevel(orderPay);
    }

    @Override
    public void insert(MemberLevelDTO memberLevelDTO) {
        memberLevelDao.insert(memberLevelDTO);
    }

    @Override
    public void update(MemberLevelDTO memberLevelDTO) {
        memberLevelDao.update(memberLevelDTO);
    }

    @Override
    public void delete(Long[] ids) {
        memberLevelDao.delete(ids);
    }

    @Override
    public String findInitLevel() {
        return memberLevelDao.findInitLevel();
    }

    @Override
    public boolean validateRange(BigDecimal range) {
        return StringUtils.isNotEmpty(memberLevelDao.validateRange(range)) ? false : true;
    }

    @Override
    public Boolean checkMemberInLevel(Long[] ids) {
        return StringUtils.isNotEmpty(memberLevelDao.checkMemberInLevel(ids)) ? false : true;
    }
}
