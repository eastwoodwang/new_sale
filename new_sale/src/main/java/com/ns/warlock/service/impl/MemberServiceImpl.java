package com.ns.warlock.service.impl;

import com.ns.warlock.dao.MemberDao;
import com.ns.warlock.dto.MemberDTO;
import com.ns.warlock.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<MemberDTO> findAll() {
        return memberDao.findAll();
    }

    @Override
    public List<MemberDTO> findAllStatis() {
        return memberDao.findAllStatis();
    }

    @Override
    public MemberDTO find(long id) {
        return memberDao.find(id);
    }

    @Override
    public void update(MemberDTO memberDTO) {
        memberDao.update(memberDTO);
    }

    @Override
    public void delete(Long[] ids) {
        memberDao.delete(ids);
    }

    @Override
    public long insert(MemberDTO memberDTO) {
        return memberDao.insert(memberDTO);
    }

    @Override
    public MemberDTO checkMemberRegister(String openId) {
        return memberDao.checkMemberRegister(openId);
    }

//    @Override
//    public List<MemberDTO> filterTopMember(long groupId) {
//        return memberDao.filterTopMember(groupId);
//    }


    @Override
    public MemberDTO checkMemberExist(String memberPhone) {
        return memberDao.checkMemberExist(memberPhone);
    }

    @Override
    public void monthClear() {
        memberDao.monthClear();
    }
}
