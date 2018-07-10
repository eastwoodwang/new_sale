package com.ns.warlock.dao;

import com.ns.warlock.dto.GroupLevelDTO;

import java.util.List;

public interface GroupLevelDao {

    /**
     * 获取全部列表
     * @return
     */
    List<GroupLevelDTO> findAll();

    /**
     * 查找
     * @param id
     * @return
     */
    GroupLevelDTO find(Long id);

    /**
     * 插入
     */
    void insert(GroupLevelDTO groupLevelDTO);

    /**
     * 更新
     * @param groupLevelDTO
     */
    void update(GroupLevelDTO groupLevelDTO);

    /**
     * 删除
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 获取初始化组等级
     * @return
     */
    String findInitGroupLevel();

}
