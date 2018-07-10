package com.ns.warlock.dao;



import com.ns.warlock.dto.AdminDTO;

import java.util.Set;

public interface AdminDao {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    AdminDTO findByUsername(String username);

    /**
     * 根据用户名获取用户权限
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

    /**
     * 根据用户名获取角色列表
     * @param username
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * 修改密码
     * @param adminDTO
     */
    void updatePassword(AdminDTO adminDTO);

}
