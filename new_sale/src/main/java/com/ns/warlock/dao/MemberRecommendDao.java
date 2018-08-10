package com.ns.warlock.dao;

import com.ns.warlock.dto.MemberRecommendDTO;
import java.util.List;

public interface MemberRecommendDao {

    /**
     * 获取某一用户的全部推荐
     * @return
     */
    List<MemberRecommendDTO> findAll(String openId);


    /**
     * 查找某一用户的真实推荐人
     * @param id
     * @return
     */
    MemberRecommendDTO find(String openId);


    /**
     * 新建推荐记录
     * @param memberDTO
     */
    long insert(MemberRecommendDTO memberRecommendDTO);
}
