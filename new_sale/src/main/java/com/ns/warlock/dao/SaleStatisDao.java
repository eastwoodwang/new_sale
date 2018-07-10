package com.ns.warlock.dao;

import com.ns.warlock.dto.SaleStatisDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SaleStatisDao {

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
    void insert(SaleStatisDTO saleStatisDTO);

    /**
     * 根据当前月查询顶级用户
     * @return
     */
    List<SaleStatisDTO> topMemberList(String month);

    /**
     * 获取叶子节点的费用总和
     * @param map
     * @return
     */
    BigDecimal sumLeafList(Map map);

}
