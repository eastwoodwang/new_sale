package com.ns.warlock.dao;

import com.ns.warlock.dto.MemberAddressDTO;

import java.util.List;

public interface MemberAddressDao {

    /**
     * 获取全部用户
     * @return
     */
    List<MemberAddressDTO> findAll(Long id);

    /**
     * 查找某一用户
     * @param id
     * @return
     */
    MemberAddressDTO find(Long id);

    /**
     * 更新某一用户
     * @param memberAddressDTO
     */
    void update(MemberAddressDTO memberAddressDTO);

    /**
     * 删除用户组
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 新建一用户
     * @param memberAddressDTO
     */
    void insert(MemberAddressDTO memberAddressDTO);

}
