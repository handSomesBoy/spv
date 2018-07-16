/*
Navicat MySQL Data Transfer

Source Server         : 84
Source Server Version : 50717
Source Host           : 172.20.32.84:3306
Source Database       : spv

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-16 17:07:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单id',
  `zindex` int(2) DEFAULT NULL COMMENT '菜单排序',
  `istype` int(1) DEFAULT NULL COMMENT '权限分类（0 菜单；1 功能）',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `code` varchar(20) DEFAULT NULL COMMENT '菜单编号',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标名称',
  `page` varchar(50) DEFAULT NULL COMMENT '菜单url',
  `state` varchar(255) DEFAULT NULL COMMENT 'true 有效 false 无效',
  `insert_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '系统管理', '0', '100', '0', '系统管理', 'system', 'el-icon-setting', '', 'true', '2017-12-20 16:22:43', '2018-01-09 19:26:36');
INSERT INTO `permission` VALUES ('2', '用户管理', '1', '1100', '0', '用户管理', 'usermanage', '', 'spv/sys/userManager/userList', 'true', '2017-12-20 16:27:03', '2018-01-09 19:26:30');
INSERT INTO `permission` VALUES ('3', '角色管理', '1', '1200', '0', '角色管理', 'rolemanage', '', 'spv/sys/roleManager/roleList', 'true', '2017-12-20 16:27:03', '2018-01-09 19:26:42');
INSERT INTO `permission` VALUES ('4', '权限管理', '1', '1300', '0', '权限管理', 'permmanage', null, '', 'true', '2017-12-30 19:17:32', '2018-01-09 19:26:48');
INSERT INTO `permission` VALUES ('5', '商品管理', '0', '300', '0', '商品管理', 'shops', 'el-icon-news', '', 'true', '2017-12-30 19:17:50', '2018-01-09 19:20:11');
INSERT INTO `permission` VALUES ('6', '渠道管理', '0', '200', '0', '渠道管理', 'channel', 'el-icon-rank', '', 'true', '2018-01-01 11:07:17', '2018-01-09 19:05:42');
INSERT INTO `permission` VALUES ('8', '订单管理', '0', '400', '0', '订单管理', 'orders', 'el-icon-bell', '', 'true', '2018-01-09 09:26:53', '2018-01-09 19:20:40');
INSERT INTO `permission` VALUES ('10', '渠道信息列表', '6', '2200', '0', '渠道信息列表', 'channelPage', null, '', 'true', '2018-01-09 19:07:05', '2018-01-09 19:31:13');
INSERT INTO `permission` VALUES ('11', '渠道会员列表', '6', '2300', '0', '渠道会员列表', 'channelUsers', null, '', 'true', '2018-01-09 19:07:52', '2018-01-18 14:08:08');
INSERT INTO `permission` VALUES ('13', '商品列表', '5', '3100', '0', '商品列表', 'shopPage', null, '', 'true', '2018-01-09 19:33:53', '2018-04-22 21:18:11');
INSERT INTO `permission` VALUES ('14', '商品订单列表', '8', '4100', '0', '商品订单列表', 'orderPage', null, ' ', 'true', '2018-01-09 19:34:33', '2018-04-22 21:17:58');
INSERT INTO `permission` VALUES ('15', '运维管理', '0', '600', '0', '运维管理', 'operation', 'el-icon-service', null, 'true', '2018-06-29 11:40:23', '2018-06-29 11:40:31');
INSERT INTO `permission` VALUES ('16', 'druid连接池', '15', '100', '0', 'druid连接池', 'druid', null, 'druid/login.html', 'true', '2018-06-14 11:42:06', '2018-07-05 11:42:10');
INSERT INTO `permission` VALUES ('17', 'swagger', '15', '200', '0', 'swagger', 'swagger', null, 'swagger-ui.html', 'true', '2018-06-29 17:18:54', '2018-06-29 17:19:00');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `descpt` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `code` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `is_use` int(1) DEFAULT '0' COMMENT '是否有效',
  `insert_uid` int(11) DEFAULT NULL COMMENT '操作用户id',
  `insert_time` datetime DEFAULT NULL COMMENT '添加数据时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理', '超级管理员', 'superman', '0', null, '2018-01-09 19:28:53', '2018-01-09 19:34:56');
INSERT INTO `role` VALUES ('2', '高级管理员', '高级管理员', 'highmanage', '0', null, '2018-01-17 13:53:23', '2018-01-18 13:39:29');
INSERT INTO `role` VALUES ('3', '经理', '经理', 'bdmanage', '1', null, '2018-01-18 13:41:47', '2018-04-22 21:15:38');
INSERT INTO `role` VALUES ('4', '质检员', '质检员', 'checkmanage', '0', null, '2018-01-18 14:03:00', '2018-04-22 21:15:59');
INSERT INTO `role` VALUES ('5', '客维员', '客维员', 'guestmanage', '0', null, '2018-01-18 14:06:48', '2018-04-22 21:16:07');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `permit_id` int(5) NOT NULL AUTO_INCREMENT,
  `role_id` int(5) NOT NULL,
  PRIMARY KEY (`permit_id`,`role_id`),
  KEY `perimitid` (`permit_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('3', '1');
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('4', '1');
INSERT INTO `role_permission` VALUES ('5', '1');
INSERT INTO `role_permission` VALUES ('5', '2');
INSERT INTO `role_permission` VALUES ('5', '3');
INSERT INTO `role_permission` VALUES ('5', '5');
INSERT INTO `role_permission` VALUES ('6', '1');
INSERT INTO `role_permission` VALUES ('6', '2');
INSERT INTO `role_permission` VALUES ('6', '3');
INSERT INTO `role_permission` VALUES ('6', '4');
INSERT INTO `role_permission` VALUES ('6', '5');
INSERT INTO `role_permission` VALUES ('7', '1');
INSERT INTO `role_permission` VALUES ('8', '1');
INSERT INTO `role_permission` VALUES ('8', '2');
INSERT INTO `role_permission` VALUES ('8', '3');
INSERT INTO `role_permission` VALUES ('8', '5');
INSERT INTO `role_permission` VALUES ('10', '1');
INSERT INTO `role_permission` VALUES ('10', '2');
INSERT INTO `role_permission` VALUES ('10', '3');
INSERT INTO `role_permission` VALUES ('10', '4');
INSERT INTO `role_permission` VALUES ('11', '1');
INSERT INTO `role_permission` VALUES ('11', '2');
INSERT INTO `role_permission` VALUES ('11', '3');
INSERT INTO `role_permission` VALUES ('11', '5');
INSERT INTO `role_permission` VALUES ('12', '1');
INSERT INTO `role_permission` VALUES ('12', '2');
INSERT INTO `role_permission` VALUES ('12', '3');
INSERT INTO `role_permission` VALUES ('13', '1');
INSERT INTO `role_permission` VALUES ('13', '2');
INSERT INTO `role_permission` VALUES ('13', '3');
INSERT INTO `role_permission` VALUES ('13', '5');
INSERT INTO `role_permission` VALUES ('14', '1');
INSERT INTO `role_permission` VALUES ('14', '2');
INSERT INTO `role_permission` VALUES ('14', '3');
INSERT INTO `role_permission` VALUES ('14', '5');
INSERT INTO `role_permission` VALUES ('15', '1');
INSERT INTO `role_permission` VALUES ('16', '1');
INSERT INTO `role_permission` VALUES ('17', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT '' COMMENT '用户名',
  `fullname` varchar(200) DEFAULT NULL COMMENT '用户全称',
  `mobile` varchar(15) DEFAULT '' COMMENT '手机号',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `insert_uid` int(11) DEFAULT NULL COMMENT '添加该用户的用户id',
  `insert_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除（0：正常；1：已删）',
  `is_job` tinyint(1) DEFAULT '0' COMMENT '是否在职（0：正常；1，离职）',
  `version` int(10) DEFAULT '0' COMMENT '更新版本',
  PRIMARY KEY (`id`),
  KEY `name` (`username`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '管理员', '12316596566', 'aaa', '96e79218965eb72c92a549dd5a330112', '1', '2017-12-29 17:27:23', '2018-07-16 10:50:56', '0', '0', '3');
INSERT INTO `user` VALUES ('3', 'sc', '业务员1', '11155556667', 'a11', '96e79218965eb72c92a549dd5a330112', '1', '2018-01-01 15:17:19', '2018-07-16 10:52:02', '0', '0', '1');
INSERT INTO `user` VALUES ('34', 'sc2', '业务员2', '13433332222', '11', '96e79218965eb72c92a549dd5a330112', '1', '2018-07-16 10:52:27', '2018-07-16 10:52:36', '0', '0', '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(5) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `userid` (`user_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('3', '5');
INSERT INTO `user_role` VALUES ('34', '4');
INSERT INTO `user_role` VALUES ('34', '5');
