package com.ns.warlock.service;

import com.ns.warlock.dao.MemberDepositDao;
import com.ns.warlock.dto.MemberDepositDTO;


import java.util.List;

/**
 * 用户充值 - service
 */
public interface MemberDepositService {

    /**
     * 所有会员的充值列表
     * @return
     */
    List<MemberDepositDao> list();

    /**
     * 新增充值
     * @param depositDTO
     */
    void add(MemberDepositDTO depositDTO);

}
