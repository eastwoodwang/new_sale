package com.ns.warlock.service.impl;

import com.ns.warlock.dao.MemberDepositDao;
import com.ns.warlock.dto.MemberDepositDTO;
import com.ns.warlock.service.MemberDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberDepositServiceImpl")
public class MemberDepositServiceImpl implements MemberDepositService {

    @Autowired
    private MemberDepositDao memberDepositDao;

    @Override
    public List<MemberDepositDao> list() {
        return memberDepositDao.list();
    }

    @Override
    public void add(MemberDepositDTO memberDepositDTO) {
        memberDepositDao.add(memberDepositDTO);
    }
}
