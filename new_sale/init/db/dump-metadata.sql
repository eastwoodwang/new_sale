use newsale;


-- ----------------------------
-- Records of ns_admin
-- ----------------------------
INSERT INTO ns_admin(id, create_date, modify_date, locked_date, login_date, login_failure_count, login_ip, name, password, username, admin_status, is_enabled, is_locked, is_system) VALUES ('1', now(), now(), null, now(), '0', null, '管理员', '21232f297a57a5a743894a0e4a801fc3', 'admin', '0', 1, 0, 1);
INSERT INTO ns_admin(id, create_date, modify_date, locked_date, login_date, login_failure_count, login_ip, name, password, username, admin_status, is_enabled, is_locked, is_system) VALUES ('2', now(), now(), null, now(), '0', null, '操作员', '21232f297a57a5a743894a0e4a801fc3', 'nxf2018', '0', 1, 0, 0);
-- ----------------------------
-- Records of ns_role
-- ----------------------------
INSERT INTO ns_role VALUES ('1', now(), now(), '拥有管理后台最高权限', 1, '超级管理员');
INSERT INTO ns_role VALUES ('2', now(), now(), '预充值权限', 0, '普通管理员');

-- ----------------------------
-- Records of fw_admin_role
-- ----------------------------
INSERT INTO ns_admin_role VALUES ('1', '1');
INSERT INTO ns_admin_role VALUES ('2', '2');


-- ----------------------------
-- Records of ns_role_authority
-- ----------------------------
INSERT INTO ns_role_authority VALUES ('1', 'admin:product');
INSERT INTO ns_role_authority VALUES ('1', 'admin:order');
INSERT INTO ns_role_authority VALUES ('1', 'admin:member');
INSERT INTO ns_role_authority VALUES ('1', 'admin:memberLevel');
INSERT INTO ns_role_authority VALUES ('1', 'admin:group');
INSERT INTO ns_role_authority VALUES ('1', 'admin:groupLevel');
INSERT INTO ns_role_authority VALUES ('1', 'admin:profile');
INSERT INTO ns_role_authority VALUES ('1', 'admin:setting');
INSERT INTO ns_role_authority VALUES ('1', 'admin:memberDeposit');
INSERT INTO ns_role_authority VALUES ('2', 'admin:memberDeposit');



-- ----------------------------
-- Records of ns_sn
-- ----------------------------
INSERT INTO ns_sn (id, create_date, modify_date, last_value, type) VALUES ('1', now(), now(), '1', '0');
INSERT INTO ns_sn (id, create_date, modify_date, last_value, type) VALUES ('2', now(), now(), '1', '1');
INSERT INTO ns_sn (id, create_date, modify_date, last_value, type) VALUES ('3', now(), now(), '1', '2');



-- ----------------------------
-- Records of ns_member_level
-- ----------------------------
INSERT INTO ns_member_level (id, level_name, min_range, max_range, priority, system) VALUES ('1', '根ROOT用户', 0, 0, 0, 1);
INSERT INTO ns_member_level (id, level_name, min_range, max_range, priority, system) VALUES ('2', '普通会员', 0, 0, 1, 0);



-- ----------------------------
-- Records of ns_member
-- ----------------------------
INSERT INTO ns_member (id, openid, nickname, header_url, register_date, last_login_date, current_owner_coast, current_leaf_coast, member_level, qr_code_url, group_id, parent_id, parent_openid, parent_tree, create_date, status, priority) VALUES (1, 'NB_ROOT_ADMIN', 'ROOT用户', null, now(), null, 0, 0, 1, null, null, null, null, null, now(), 1, 0);


-- ----------------------------------
-- Records of ns_member_group_level
-- ----------------------------------
INSERT INTO ns_member_group_level (id, group_level_name, min_range, max_range, discount_rate, priority) VALUES ('1', '初级团队等级', '0', '0', '5', '1');
