package com.ns.warlock.service.impl;

import com.ns.warlock.dao.MemberRecommendDao;
import com.ns.warlock.dto.MemberRecommendDTO;
import com.ns.warlock.service.MemberRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberRecommendServiceImpl")
public class MemberRecommendServiceImpl implements MemberRecommendService {

    @Autowired
    private MemberRecommendDao memberRecommendDao;

    /**
     * 获取某一用户的全部推荐
     * @return
     */
    @Override
    public List<MemberRecommendDTO> findAll(String openId) {
        return memberRecommendDao.findAll(openId);
    }

    /**
     * 查找某一用户的真实推荐人
     * @param id
     * @return
     */
    @Override
    public MemberRecommendDTO find(String openId) {
        return memberRecommendDao.find(openId);
    }

    /**
     * 新建推荐记录
     * @param memberDTO
     */
    @Override
    public long insert(MemberRecommendDTO memberRecommendDTO) {
        return memberRecommendDao.insert(memberRecommendDTO);
    }
}
