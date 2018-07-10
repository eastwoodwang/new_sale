package com.ns.warlock.service.impl;

import com.ns.warlock.dao.GroupDao;
import com.ns.warlock.dto.GroupDTO;
import com.ns.warlock.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("groupServiceImpl")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<GroupDTO> findAll() {
        return groupDao.findAll();
    }

    @Override
    public GroupDTO find(long id) {
        return groupDao.find(id);
    }

    @Override
    public void update(GroupDTO groupDTO) {
        groupDao.update(groupDTO);
    }

    @Override
    public void delete(Long[] ids) {
        groupDao.delete(ids);
    }

    @Override
    public GroupDTO findByCreateUser(String openId) {
        return groupDao.findByCreateUser(openId);
    }

    @Override
    public void create(GroupDTO groupDTO) {
        groupDao.insert(groupDTO);
    }

    @Override
    public void monthGroupClear() {
        groupDao.monthGroupClear();
    }
}
