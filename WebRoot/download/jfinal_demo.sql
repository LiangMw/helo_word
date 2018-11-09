/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50712
Source Host           : 127.0.0.1:3306
Source Database       : jfinal_demo

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2018-03-19 14:10:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_function
-- ----------------------------
DROP TABLE IF EXISTS `t_function`;
CREATE TABLE `t_function` (
  `id` int(11) NOT NULL,
  `functionname` varchar(200) NOT NULL,
  `fatherid` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL COMMENT '功能编码',
  `rel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_function
-- ----------------------------
INSERT INTO `t_function` VALUES ('1', '系统设置', '0', null, '3', 'sys', null);
INSERT INTO `t_function` VALUES ('2', '用户管理', '1', '/sys_user', '1', 'sys_user', 'sys_user');
INSERT INTO `t_function` VALUES ('3', '后台权限管理', '1', '/sys_auth', '2', 'sys_auth', 'sys_auth');
INSERT INTO `t_function` VALUES ('4', '用户操作日志', '1', '/sys_log', '3', 'sys_log', 'sys_log');
INSERT INTO `t_function` VALUES ('5', '后台管理', '0', null, '1', null, null);
INSERT INTO `t_function` VALUES ('6', '内容添加', '5', '/testjsp', '1', 'addcontent', 'addcontent');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_rolename` (`rolename`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('15', '后台管理');

-- ----------------------------
-- Table structure for t_rolefunction
-- ----------------------------
DROP TABLE IF EXISTS `t_rolefunction`;
CREATE TABLE `t_rolefunction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `functionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`roleid`),
  KEY `FK_Reference_3` (`functionid`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`functionid`) REFERENCES `t_function` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rolefunction
-- ----------------------------
INSERT INTO `t_rolefunction` VALUES ('4', '15', '6');
INSERT INTO `t_rolefunction` VALUES ('5', '15', '4');

-- ----------------------------
-- Table structure for t_roleuser
-- ----------------------------
DROP TABLE IF EXISTS `t_roleuser`;
CREATE TABLE `t_roleuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`roleid`),
  KEY `FK_Reference_4` (`userid`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_roleuser
-- ----------------------------
INSERT INTO `t_roleuser` VALUES ('2', '15', '7');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `idcard` varchar(100) DEFAULT NULL,
  `bankcardno` varchar(50) DEFAULT NULL,
  `unitname` varchar(100) DEFAULT NULL,
  `salaryid` varchar(100) DEFAULT NULL,
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `sex` int(1) DEFAULT '1' COMMENT '1.男 0.女',
  `state` int(11) DEFAULT '1' COMMENT '1.在用  -1.停用 -2.不可用',
  `memo` varchar(500) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `creatid` int(20) DEFAULT NULL,
  `creatlevle` int(20) DEFAULT NULL,
  `isfeedback` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='state\r\n---------\r\n1:ok \r\n0:disable\r\n-1:禁用';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '13090012313112', null, null, null, '超级管理员', '1', '1', null, null, null, '11735d6092e36af59bbabfff77f4595b617043993911e864db656af442fae13e', '2a0ed4f4dd9f7716', null, null, null);
INSERT INTO `t_user` VALUES ('7', 'zhangsan', null, null, null, null, '张三', '1', '1', null, '2017-06-10 15:02:35', null, 'c82c5aeb6057f428d21b913e468ed8e2e8e5f865acb3682e485f0158257cf04d', '3YDSU4LU', null, null, null);

-- ----------------------------
-- Table structure for t_userlog
-- ----------------------------
DROP TABLE IF EXISTS `t_userlog`;
CREATE TABLE `t_userlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `loginip` varchar(50) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT 'type:1登录 2：退出',
  `opcontent` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4346 DEFAULT CHARSET=utf8 COMMENT='登录及操作日志表';

-- ----------------------------
-- Records of t_userlog
-- ----------------------------
INSERT INTO `t_userlog` VALUES ('4267', '1', '本地', '2017-06-10 13:36:35', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4268', '1', null, '2017-06-10 13:39:26', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4269', '1', '本地', '2017-06-10 13:39:38', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4270', '1', '本地', '2017-06-10 13:40:21', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4271', '1', '本地', '2017-06-10 13:41:55', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4272', '1', '本地', '2017-06-10 13:42:31', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4273', '1', '本地', '2017-06-10 15:01:19', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4274', '1', '本地', '2017-06-10 15:02:11', '5', '添加角色：后台管理，编号：15');
INSERT INTO `t_userlog` VALUES ('4275', '1', null, '2017-06-10 15:02:45', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4276', '7', '本地', '2017-06-10 15:02:53', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4277', '7', '本地', '2017-06-10 15:03:21', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4278', '7', null, '2017-06-10 15:03:32', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4279', '1', '本地', '2017-06-10 15:03:37', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4280', '1', null, '2017-06-10 15:21:48', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4281', '1', '本地', '2017-06-10 15:21:50', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4282', '1', null, '2017-06-10 15:21:52', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4283', '1', '本地', '2017-06-10 15:21:57', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4284', '1', null, '2017-06-10 15:45:35', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4285', '1', '本地', '2017-06-10 15:45:37', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4286', '1', '本地', '2017-06-10 15:52:06', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4287', '1', '本地', '2017-06-10 16:00:14', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4288', '1', '本地', '2017-06-10 16:09:25', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4289', '1', '本地', '2017-06-10 16:10:35', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4290', '1', null, '2017-06-10 16:10:38', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4291', '1', '127.0.0.1', '2017-06-12 08:09:15', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4292', '1', null, '2017-06-12 08:28:45', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4293', '1', '127.0.0.1', '2017-06-12 08:28:46', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4294', '1', '127.0.0.1', '2017-06-12 08:28:50', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4295', '1', '127.0.0.1', '2017-06-12 08:28:51', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4296', '1', '127.0.0.1', '2017-06-12 08:28:51', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4297', '1', '127.0.0.1', '2017-06-12 08:28:52', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4298', '1', '127.0.0.1', '2017-06-12 08:28:53', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4299', '1', '127.0.0.1', '2017-06-12 08:28:53', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4300', '1', '127.0.0.1', '2017-06-12 08:28:53', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4301', '1', '127.0.0.1', '2017-06-12 08:28:53', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4302', '1', '127.0.0.1', '2017-06-12 08:28:54', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4303', '1', '127.0.0.1', '2017-06-12 09:22:54', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4304', '1', '127.0.0.1', '2017-06-12 09:22:57', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4305', '1', null, '2017-06-12 09:23:10', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4306', '1', '127.0.0.1', '2017-06-12 09:23:11', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4307', '1', '127.0.0.1', '2017-06-12 09:23:52', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4308', '1', null, '2017-06-12 09:24:02', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4309', '1', '127.0.0.1', '2017-06-12 09:24:03', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4310', '1', '127.0.0.1', '2017-06-12 09:27:09', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4311', '1', '127.0.0.1', '2017-06-12 09:27:34', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4312', '1', '127.0.0.1', '2017-06-12 09:30:21', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4313', '1', '127.0.0.1', '2017-06-12 09:32:11', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4314', '1', '127.0.0.1', '2017-06-12 09:34:31', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4315', '1', '127.0.0.1', '2017-06-12 09:34:51', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4316', '1', '127.0.0.1', '2017-06-12 09:39:48', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4317', '1', '127.0.0.1', '2017-06-12 09:39:59', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4318', '1', '127.0.0.1', '2017-06-12 09:40:01', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4319', '1', null, '2017-06-12 09:40:05', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4320', '1', '127.0.0.1', '2017-06-12 09:40:07', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4321', '1', null, '2017-06-12 09:41:28', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4322', '1', '127.0.0.1', '2017-06-12 09:41:51', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4323', '1', '127.0.0.1', '2017-06-12 09:45:27', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4324', '1', '127.0.0.1', '2017-06-12 09:47:23', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4325', '1', '127.0.0.1', '2017-06-12 09:52:24', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4326', '1', '127.0.0.1', '2017-06-12 09:54:08', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4327', '1', '127.0.0.1', '2017-06-12 09:54:28', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4328', '1', '127.0.0.1', '2017-06-12 09:56:02', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4329', '1', '127.0.0.1', '2017-06-12 09:56:40', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4330', '1', '127.0.0.1', '2017-06-12 09:58:20', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4331', '1', '127.0.0.1', '2017-06-12 10:09:46', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4332', '1', '127.0.0.1', '2017-06-12 10:11:38', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4333', '1', '127.0.0.1', '2017-06-12 10:14:13', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4334', '1', '127.0.0.1', '2017-06-12 10:16:06', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4335', '1', '127.0.0.1', '2017-06-12 10:31:29', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4336', '1', '127.0.0.1', '2017-06-12 10:37:17', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4337', '1', '127.0.0.1', '2017-06-12 10:48:42', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4338', '1', '127.0.0.1', '2017-06-12 14:13:14', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4339', '1', null, '2017-06-12 14:14:33', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4340', '1', '127.0.0.1', '2017-06-12 14:14:34', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4341', '1', '127.0.0.1', '2017-06-12 15:02:42', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4342', '1', '本地', '2017-06-22 10:14:39', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4343', '1', null, '2017-06-22 10:15:19', '4', '退出登录');
INSERT INTO `t_userlog` VALUES ('4344', '1', '本地', '2017-09-05 19:53:24', '4', '登录');
INSERT INTO `t_userlog` VALUES ('4345', '1', '本地', '2017-12-06 10:17:03', '4', '登录');
