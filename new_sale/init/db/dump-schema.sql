-- MySQL dump 10.13  Distrib 5.5.28
--
-- Host: localhost    Database: newsale
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

SET GLOBAL log_bin_trust_function_creators = 1;
SET FOREIGN_KEY_CHECKS=0;


DROP DATABASE IF EXISTS `newsale`;
CREATE DATABASE `newsale`;

-- ----------------------------
-- Drop user if exist
-- ----------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS newsale.drop_user_if_exists;
CREATE PROCEDURE newsale.drop_user_if_exists()
BEGIN
    DECLARE foo BIGINT(20) DEFAULT 0;

    SELECT COUNT(*) INTO foo FROM mysql.user WHERE User = 'newsale' AND Host = '%';
    IF foo > 0 THEN
        DROP USER 'newsale'@'%';
    END IF;

    SELECT COUNT(*) INTO foo FROM mysql.user WHERE User = 'newsale' AND Host = 'localhost';
    IF foo > 0 THEN
        DROP USER 'newsale'@'localhost';
    END IF;
  END $$
DELIMITER ;

CALL newsale.drop_user_if_exists();
DROP PROCEDURE IF EXISTS newsale.drop_user_if_exists;


CREATE USER newsale IDENTIFIED BY 'wang465200';

GRANT ALL on *.* TO 'root'@'%';
GRANT ALL on newsale.* TO 'newsale'@'%' IDENTIFIED BY 'wang465200';
GRANT ALL on newsale.* TO 'newsale'@'localhost' IDENTIFIED BY 'wang465200';
GRANT FILE on *.* TO 'newsale'@'localhost';
GRANT SUPER on *.* to 'newsale'@'%';
GRANT SUPER on *.* to 'newsale'@'localhost';

use newsale;

-- -------------------------------
-- Table structure for `ns_admin`
-- -------------------------------
DROP TABLE IF EXISTS `ns_admin`;
CREATE TABLE `ns_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_failure_count` int(11) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  `admin_status` int(11) DEFAULT NULL,
  `is_system` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- -----------------------------------
-- Table structure for `ns_admin_role`
-- -----------------------------------
DROP TABLE IF EXISTS `ns_admin_role`;
CREATE TABLE `ns_admin_role` (
  `admin_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ---------------------------------
-- Table structure for `ns_role`
-- ---------------------------------
DROP TABLE IF EXISTS `ns_role`;
CREATE TABLE `ns_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `modify_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




-- ----------------------------------------
-- Table structure for `ns_role_authority`
-- ----------------------------------------
DROP TABLE IF EXISTS `ns_role_authority`;
CREATE TABLE `ns_role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authorities` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------
-- Table structure for `ns_sn`
-- --------------------------------
DROP TABLE IF EXISTS `ns_sn`;
CREATE TABLE `ns_sn` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'sn ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `modify_date` datetime COMMENT '修改时间',
  `last_value` bigint(20) NOT NULL COMMENT '最后一个值',
  `type` int(11) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;


-- ------------------------------------
-- Table structure for `ns_member_level`
-- -----------------------------------
DROP TABLE IF EXISTS `ns_member_level`;
CREATE TABLE `ns_member_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `level_name` VARCHAR(10) NOT NULL COMMENT '等级名称',
  `min_range` DECIMAL NOT NULL COMMENT '等级最低晋级消费',
  `max_range` DECIMAL NOT NULL COMMENT '等级最高消费',
  `priority` TINYINT(2) NOT NULL COMMENT '优先级',
  `system` TINYINT(1) DEFAULT 0 COMMENT '是否为系统会员 1:是 0：否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员等级表';



-- ------------------------------------
-- Table structure for `ns_member`
-- ------------------------------------
DROP TABLE IF EXISTS `ns_member`;
CREATE TABLE `ns_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `openid` VARCHAR(50) NOT NULL COMMENT '微信openid',
  `nickname` VARCHAR(30) COMMENT '微信昵称',
  `header_url` VARCHAR(200) COMMENT '微信头像',
  `sex` VARCHAR(1) COMMENT '微信性别',
  `register_date` DATETIME NOT NULL COMMENT '注册日期',
  `last_login_date` DATETIME COMMENT '上次登录时间',
  `current_owner_coast` DECIMAL(10,2) COMMENT '当月个人消费累计',
  `current_leaf_coast` DECIMAL(10,2) COMMENT '当月团队消费累计',
  `member_level` bigint(20) DEFAULT NULL COMMENT '会员等级',
  `qr_code_url` VARCHAR(200) COMMENT '二维码地址路径',
  `group_id` bigint(20) COMMENT '所属团队',
  `parent_id` bigint(20) COMMENT '父节点会员ID',
  `parent_openid` VARCHAR(50) COMMENT '父节点会员openid',
  `parent_tree` VARCHAR(200) COMMENT '树结构 如:2,3,4,5',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  `channel_type` TINYINT(1) DEFAULT 0 COMMENT '用户来源 0:微信 1:自己注册',
  `status` TINYINT(1) COMMENT '账号状态 1:生效 0:失效',
  `priority` INT NOT NULL COMMENT '层级 0:系统默认树节点 然后依次下来',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员表';


-- ---------------------------------------
-- Table structure for `ns_member_deposit`
-- ---------------------------------------
DROP TABLE IF EXISTS `ns_member_deposit`;
CREATE TABLE `ns_member_deposit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` VARCHAR(20) NOT NULL COMMENT '会员ID',
  `date` DATETIME NOT NULL COMMENT '充值时间',
  `credit` DECIMAL(10,2) NOT NULL COMMENT '充值金额',
  `memo` VARCHAR(100) NULL COMMENT '充值备注',
  `operator` VARCHAR(10) NOT NULL COMMENT '操作员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员充值列表';



-- ------------------------------------------
-- Table structure for `ns_sale_statitics`
-- ------------------------------------------
DROP TABLE IF EXISTS `ns_sale_statis`;
CREATE TABLE `ns_sale_statis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '用户id',
  `month` VARCHAR(20) NOT NULL COMMENT '存放日期 YYYY-MM-DD格式',
  `group_id` bigint(20) NOT NULL COMMENT '用户所在组ID',
  `rate` DECIMAL COMMENT '返点率',
  `person_sale` DECIMAL(10,2) NOT NULL COMMENT '个人销售金额',
  `leaf_sale` DECIMAL(10,2) NOT NULL COMMENT '叶子销售总金额',
  `group_sale` DECIMAL(10,2) NOT NULL COMMENT '所在团队总销售金额',
  `sale_gain` DECIMAL(10,2) NOT NULL COMMENT '个人返点金额',
  `leaf_sale_gain` DECIMAL(10,2) COMMENT '个人叶子节点销售返点金额总和',
  `parent_member_id` bigint(20) NOT NULL COMMENT '上一级member_id',
  `top_member_id` bigint(20) NOT NULL COMMENT '顶级用户member_id',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '统计数据表(存放非当月数据记录)';



-- ----------------------------------------
-- Table structure for `ns_member_address`
-- ----------------------------------------
DROP TABLE IF EXISTS `ns_member_address`;
CREATE TABLE `ns_member_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '用户id',
  `address` VARCHAR(200) NOT NULL COMMENT '用户地址',
  `zipCode` VARCHAR(60) COMMENT '邮编',
  `phone` VARCHAR(13) NOT NULL COMMENT '收货人电话',
  `name` VARCHAR(20) NOT NULL COMMENT '收货人姓名',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员地址表';


-- ----------------------------------------
-- Table structure for `ns_cart`
-- ----------------------------------------
DROP TABLE IF EXISTS `ns_cart`;
CREATE TABLE `ns_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员购物车';


-- ----------------------------------------
-- Table structure for `ns_cart_item`
-- ----------------------------------------
DROP TABLE IF EXISTS `ns_cart_item`;
CREATE TABLE `ns_cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cart_id` bigint(20) NOT NULL COMMENT '购物车id',
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `quantity` int(4) NOT NULL COMMENT '数量',
  `price` DECIMAL(10,2) NOT NULL COMMENT '商品临时价格',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '会员购物车里项目';


-- ---------------------------------------
-- Table structure for `ns_member_group`
-- ---------------------------------------
DROP TABLE IF EXISTS `ns_member_group`;
CREATE TABLE `ns_member_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_count` BIGINT(20) NOT NULL COMMENT '成员数',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `create_user`  VARCHAR(50) NOT NULL COMMENT '创建用户openid',
  `coast` DECIMAL(10,2) COMMENT '当月团队消费累计',
  `group_level` BIGINT(20) DEFAULT NULL COMMENT '团队等级',
  `status` TINYINT(20) COMMENT '团队状态 1:活跃 0:锁定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '团队表';



-- -------------------------------------------
-- Table structure for `ns_member_group_level`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_member_group_level`;
CREATE TABLE `ns_member_group_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_level_name` VARCHAR(100) NOT NULL COMMENT '团队等级',
  `min_range` DECIMAL(10,2) NOT NULL COMMENT '等级最小值',
  `max_range` DECIMAL(10,2) NOT NULL COMMENT '等级最大值',
  `discount_rate` DECIMAL(10,2) COMMENT '返点率',
  `priority` TINYINT(2) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '团队等级表';


-- -------------------------------------------
-- Table structure for `ns_product`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_product`;
CREATE TABLE `ns_product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sn` VARCHAR(20) NOT NULL COMMENT '编号',
  `name` VARCHAR(100) NOT NULL COMMENT '商品名',
  `price` DECIMAL(10, 2) NOT NULL COMMENT '销售价格',
  `member_price` DECIMAL(10, 2) COMMENT '会员价格',
  `display_image` VARCHAR(100) COMMENT '商品展示图片',
  `stock` BIGINT(20) COMMENT '库存',
  `marketable` VARCHAR(2) NOT NULL COMMENT '商品状态 1：上架 2：下架',
  `allocate_stock` BIGINT(20) COMMENT '已结分配的库存',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '商品表';



-- -------------------------------------------
-- Table structure for `ns_product_image`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_product_image`;
CREATE TABLE `ns_product_image` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` VARCHAR(20) COMMENT '标题',
  `source` VARCHAR(100) COMMENT '原图片',
  `large` VARCHAR(100) COMMENT '大图片',
  `medium` VARCHAR(100) COMMENT '中图片',
  `thumbnail` VARCHAR(100) COMMENT '小图片',
  `image_order` int NOT NULL COMMENT '排序',
  `product_sn` VARCHAR(50) NOT NULL COMMENT '商品sn',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '商品图片表';



-- -------------------------------------------
-- Table structure for `ns_order`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_order`;
CREATE TABLE `ns_order` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_sn` VARCHAR(20) NOT NULL COMMENT '编号',
  `member_id` BIGINT(20) NOT NULL COMMENT '会员id',
  `pay_date` DATETIME COMMENT '支付时间',
  `address_id` VARCHAR(100) NOT NULL COMMENT '地址',
  `memo` VARCHAR(100) COMMENT '附言',
  `total_amount` DECIMAL(10, 2) COMMENT '订单总价格',
  `shipping_sn` VARCHAR(20) COMMENT '快递id',
  `shipping_status` TINYINT(1) COMMENT '快递状态',
  `order_status` TINYINT(1) NOT NULL COMMENT '订单状态',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_date` DATETIME COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '订单表';


-- -------------------------------------------
-- Table structure for `ns_order_item`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_order_item`;
CREATE TABLE `ns_order_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` BIGINT(20) NOT NULL COMMENT '订单ID',
  `product_id` VARCHAR(20) NOT NULL COMMENT '商品ID',
  `order_price` DECIMAL(10, 2) NULL COMMENT '价格',
  `quantity` BIGINT(20) NOT NULL COMMENT '数量',
  `total_price` DECIMAL(10, 2) NOT NULL COMMENT '总价',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '订单项表';


-- -------------------------------------------
-- Table structure for `ns_member_recommend`
-- -------------------------------------------
DROP TABLE IF EXISTS `ns_member_recommend`;
CREATE TABLE `ns_member_recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `openid` varchar(50) NOT NULL COMMENT '当前用户微信openid',
  `parent_openid` varchar(30) DEFAULT NULL COMMENT '推荐用户的openId',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户推荐记录表';
