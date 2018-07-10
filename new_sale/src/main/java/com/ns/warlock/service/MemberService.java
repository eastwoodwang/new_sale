package com.ns.warlock.service;

import com.ns.warlock.dto.MemberDTO;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberService {

    /**
     * 获取全部用户
     * @return
     */
    List<MemberDTO> findAll();

    /**
     * 获取用户以及对于的统计数据
     * @return
     */
    List<MemberDTO> findAllStatis();

//    /**
//     * 获取分组并剔除顶级用户
//     * @return
//     */
//    List<MemberDTO> filterTopMember(long groupId);

    /**
     * 查找某一用户
     * @param id
     * @return
     */
    MemberDTO find(long id);

    /**
     * 更新某一用户
     * @param memberDTO
     */
    void update(MemberDTO memberDTO);

    /**
     * 删除用户组
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 新建一用户
     * @param memberDTO
     */
    long insert(MemberDTO memberDTO);

    /**
     * 检查用户是否已经注册
     * @return
     */
    MemberDTO checkMemberRegister(String openId);

    /**
     * 清理当月数据
     */
    void monthClear();

    /**
     * 根据手机号查询客户是否存在
     * @param memberPhone
     * @return
     */
    MemberDTO checkMemberExist(String memberPhone);

}
