package com.ns.warlock.service.impl;

import com.ns.warlock.dao.MemberAddressDao;
import com.ns.warlock.dto.MemberAddressDTO;
import com.ns.warlock.service.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberAddressServiceImpl")
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private MemberAddressDao memberAddressDao;


    @Override
    public List<MemberAddressDTO> findAll(Long id) {
        return memberAddressDao.findAll(id);
    }

    @Override
    public MemberAddressDTO find(Long id) {
        return memberAddressDao.find(id);
    }

    @Override
    public void update(MemberAddressDTO memberAddressDTO) {
        memberAddressDao.update(memberAddressDTO);
    }

    @Override
    public void delete(Long[] ids) {
        memberAddressDao.delete(ids);
    }

    @Override
    public void create(MemberAddressDTO memberAddressDTO) {
        memberAddressDao.insert(memberAddressDTO);
    }
}
