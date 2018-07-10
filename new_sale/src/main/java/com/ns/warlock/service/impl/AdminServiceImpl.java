package com.ns.warlock.service.impl;

import com.ns.warlock.Principal;
import com.ns.warlock.dao.AdminDao;
import com.ns.warlock.dto.AdminDTO;
import com.ns.warlock.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public AdminDTO findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return adminDao.findPermissions(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        return adminDao.findRoles(username);
    }


    @Override
    public AdminDTO getCurrent() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null) {
                return adminDao.findByUsername(principal.getUsername());
            }
        }
        return null;
    }

    @Override
    public void updatePassword(AdminDTO adminDTO) {
        adminDao.updatePassword(adminDTO);
    }
}
