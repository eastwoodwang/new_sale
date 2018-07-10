package com.ns.warlock.dao;

import com.ns.warlock.dto.MemberDepositDTO;

import java.util.List;

public interface MemberDepositDao {

    /**
     * 所有会员的充值列表
     * @return
     */
    List<MemberDepositDao> list();

    /**
     * 新增充值
     * @param memberDepositDTO
     */
    void add(MemberDepositDTO memberDepositDTO);

}
