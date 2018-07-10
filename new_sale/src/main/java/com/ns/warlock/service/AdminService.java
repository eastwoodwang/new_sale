package com.ns.warlock.service;

import com.ns.warlock.dto.AdminDTO;

import java.util.Set;

/**
 * 管理员 - service
 */
public interface AdminService {

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
     * 获取当前用户
     * @return
     */
    AdminDTO getCurrent();

    /**
     * 修改密码
     * @param adminDTO
     */
    void updatePassword(AdminDTO adminDTO);

}
