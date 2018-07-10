package com.ns.warlock.service;

import com.ns.warlock.dto.SaleStatisDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface SaleStatisService {

    /**
     * 获取用户销售金额
     * @param memberId
     * @return
     */
    List<SaleStatisDTO> findList(String memberId);

    /**
     * 获取所有叶子节点信息
     * @param memberId
     * @return
     */
    List<SaleStatisDTO> leafList(String memberId);

    /**
     * 更新用户
     * @param saleStatisDTO
     */
    void update(SaleStatisDTO saleStatisDTO);

    /**
     * 持久化
     * @param saleStatisDTO
     */
    void create(SaleStatisDTO saleStatisDTO);

    /**
     * 根据当前月查询顶级用户
     * @return
     */
    List<SaleStatisDTO> topMemberList(String month);

    /**
     * 获取叶子节点及下属所有的费用总和
     * @param groupId
     * @param memberId
     * @param month
     * @return
     */
    BigDecimal sumLeafList(long groupId, long memberId, String month);

}
