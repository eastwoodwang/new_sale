package com.ns.warlock.service;


import com.ns.warlock.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    /**
     * 获取全部用户
     * @return
     */
    List<GroupDTO> findAll();

    /**
     * 查找某一组
     * @param id
     * @return
     */
    GroupDTO find(long id);

    /**
     * 更新某一组
     * @param groupDTO
     */
    void update(GroupDTO groupDTO);

    /**
     * 删除用户组
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 根据创建人openID找到所在组
     */
    GroupDTO findByCreateUser(String openId);

    /**
     * 新建组
     * @param groupDTO
     */
    void create(GroupDTO groupDTO);

    /**
     * 按月清理
     */
    void monthGroupClear();
}
