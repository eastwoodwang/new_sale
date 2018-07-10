package com.ns.warlock.service;

import com.ns.warlock.dto.MemberAddressDTO;

import java.util.List;

/**
 * 用户收件地址
 */
public interface MemberAddressService {

    /**
     * 获取用户所有地址
     * @return
     */
    List<MemberAddressDTO> findAll(Long id);

    /**
     * 获取用户地址
     * @param id
     * @return
     */
    MemberAddressDTO find(Long id);

    /**
     * 修改用户地址
     * @param memberAddressDTO
     */
    void update(MemberAddressDTO memberAddressDTO);

    /**
     * 删除用户地址
     * @param ids
     */
    void delete(Long[] ids);

    /**
     * 创建一个新地址
     * @param memberAddressDTO
     */
    void create(MemberAddressDTO memberAddressDTO);
}
