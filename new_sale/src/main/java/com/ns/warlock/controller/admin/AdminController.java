package com.ns.warlock.controller.admin;

import com.ns.warlock.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("/admin")
@Controller("adminController")
public class AdminController {

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;




}
