package com.ns.warlock.service.impl;


import com.ns.warlock.dao.GroupLevelDao;
import com.ns.warlock.dto.GroupLevelDTO;
import com.ns.warlock.service.GroupLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("groupLevelServiceImpl")
public class GroupLevelServiceImpl implements GroupLevelService {

    @Autowired
    private GroupLevelDao groupLevelDao;

    @Override
    public List<GroupLevelDTO> findAll() {
        return groupLevelDao.findAll();
    }

    @Override
    public GroupLevelDTO find(Long id) {
        return groupLevelDao.find(id);
    }

    @Override
    public void insert(GroupLevelDTO groupLevelDTO) {
        groupLevelDao.insert(groupLevelDTO);
    }

    @Override
    public void update(GroupLevelDTO groupLevelDTO) {
        groupLevelDao.update(groupLevelDTO);
    }

    @Override
    public void delete(Long[] ids) {
        groupLevelDao.delete(ids);
    }

    @Override
    public String findInitGroupLevel() {
        return groupLevelDao.findInitGroupLevel();
    }
}
