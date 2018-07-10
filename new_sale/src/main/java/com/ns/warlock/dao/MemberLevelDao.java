package com.ns.warlock.dao;

import com.ns.warlock.dto.MemberLevelDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MemberLevelDao {

    /**
     * 获取全部列表
     * @return
     */
    List<MemberLevelDTO> findAll();

    /**
     * 查找
     * @param id
     * @return
     */
    MemberLevelDTO find(Long id);

    /**
     * 获取当前金额适合的会员等级
     * @return
     */
    String selectPromptLevel(BigDecimal orderPay);

    /**
     * 插入
     */
    void insert(MemberLevelDTO memberLevelDTO);

    /**
     * 更新
     * @param memberLevelDTO
     */
    void update(MemberLevelDTO memberLevelDTO);

    /**
     * 删除
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 获取初级会员
     * @return
     */
    String findInitLevel();

    /**
     * 比较范围是否合规
     * @return
     */
    String validateRange(@Param("range") BigDecimal range);

    /**
     * 检查有无正在使用的level
     * @param ids
     * @return
     */
    String checkMemberInLevel(Long[] ids);
}
