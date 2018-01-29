/*
Navicat MySQL Data Transfer

Source Server         : 172.18.5.110
Source Server Version : 50525
Source Host           : 172.18.5.110:3306
Source Database       : ard

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-01-29 19:55:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ard_easylife
-- ----------------------------
DROP TABLE IF EXISTS `ard_easylife`;
CREATE TABLE `ard_easylife` (
  `name` varchar(30) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `appUrl` varchar(512) DEFAULT NULL,
  UNIQUE KEY `name` (`name`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_easylife
-- ----------------------------

-- ----------------------------
-- Table structure for ard_news
-- ----------------------------
DROP TABLE IF EXISTS `ard_news`;
CREATE TABLE `ard_news` (
  `news_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(30) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `content` mediumtext,
  `publishtime` datetime DEFAULT NULL,
  UNIQUE KEY `news_id` (`news_id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_news
-- ----------------------------

-- ----------------------------
-- Table structure for ard_news_attach
-- ----------------------------
DROP TABLE IF EXISTS `ard_news_attach`;
CREATE TABLE `ard_news_attach` (
  `news_id` varchar(64) NOT NULL,
  `thumb_url1` varchar(255) DEFAULT NULL,
  `thumb_url2` varchar(255) DEFAULT NULL,
  `thumb_url3` varchar(255) DEFAULT NULL,
  KEY `news_id` (`news_id`),
  CONSTRAINT `ard_news_attach_ibfk_1` FOREIGN KEY (`news_id`) REFERENCES `ard_news` (`news_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_news_attach
-- ----------------------------

-- ----------------------------
-- Table structure for ard_news_hot
-- ----------------------------
DROP TABLE IF EXISTS `ard_news_hot`;
CREATE TABLE `ard_news_hot` (
  `news_id` varchar(64) DEFAULT NULL,
  `readnumber` int(11) DEFAULT NULL,
  `goodnumber` int(11) DEFAULT NULL,
  KEY `news_id` (`news_id`),
  CONSTRAINT `ard_news_hot_ibfk_1` FOREIGN KEY (`news_id`) REFERENCES `ard_news` (`news_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_news_hot
-- ----------------------------

-- ----------------------------
-- Table structure for ard_news_theme
-- ----------------------------
DROP TABLE IF EXISTS `ard_news_theme`;
CREATE TABLE `ard_news_theme` (
  `theme_id` int(11) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `news_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`theme_id`),
  UNIQUE KEY `theme_id` (`theme_id`),
  KEY `type_id` (`type_id`),
  KEY `news_id` (`news_id`),
  CONSTRAINT `ard_news_theme_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `ard_news_type` (`type_id`) ON DELETE CASCADE,
  CONSTRAINT `ard_news_theme_ibfk_2` FOREIGN KEY (`news_id`) REFERENCES `ard_news` (`news_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_news_theme
-- ----------------------------

-- ----------------------------
-- Table structure for ard_news_type
-- ----------------------------
DROP TABLE IF EXISTS `ard_news_type`;
CREATE TABLE `ard_news_type` (
  `type_id` int(11) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_id` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_news_type
-- ----------------------------

-- ----------------------------
-- Table structure for ard_permission
-- ----------------------------
DROP TABLE IF EXISTS `ard_permission`;
CREATE TABLE `ard_permission` (
  `source_id` int(11) NOT NULL,
  `source_url` varchar(255) DEFAULT NULL,
  `source_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_permission
-- ----------------------------

-- ----------------------------
-- Table structure for ard_role
-- ----------------------------
DROP TABLE IF EXISTS `ard_role`;
CREATE TABLE `ard_role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(64) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_role
-- ----------------------------

-- ----------------------------
-- Table structure for ard_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ard_role_permission`;
CREATE TABLE `ard_role_permission` (
  `role_id` int(11) DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `role_id` (`role_id`),
  CONSTRAINT `ard_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `ard_role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for ard_user
-- ----------------------------
DROP TABLE IF EXISTS `ard_user`;
CREATE TABLE `ard_user` (
  `user_id` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `sex` tinyint(4) NOT NULL DEFAULT '0',
  `status` mediumint(16) DEFAULT '0',
  `level` mediumint(16) DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user
-- ----------------------------
INSERT INTO `ard_user` VALUES ('229160943', '3dc87e47c4c41231e15b1298c4cac6e8', '7b8b85e5d884fc4aa7e62546adf482be', '0', '0', '0', '2017-11-28 15:27:24', null);
INSERT INTO `ard_user` VALUES ('fsdafasd ', 'fddddddddddddsafd', null, '0', '4', '0', '2017-11-15 17:28:22', '2017-11-15 17:39:37');
INSERT INTO `ard_user` VALUES ('450190.4826065154', 'ab03efc3c3ff01608444a7f679465ab5', '2099c57774208b664fdec47b4247593c', '0', '0', '0', '2017-11-09 14:44:38', null);
INSERT INTO `ard_user` VALUES ('926467343', 'b4b086d02b926126c07b95ce4d097daf', 'e3d88e7c2dd57e965491ec51cedde751', '0', '0', '0', '2017-12-26 18:20:52', null);
INSERT INTO `ard_user` VALUES ('325753.3576613184', 'c2848d81d113705f0ac433b021e07946', '0311fe280e26a4f39e1bec3d4015bfac', '0', '0', '0', '2017-11-09 13:53:03', null);

-- ----------------------------
-- Table structure for ard_user_account
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_account`;
CREATE TABLE `ard_user_account` (
  `user_id` varchar(30) NOT NULL,
  `account_type` tinyint(4) NOT NULL,
  `balance` double DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`account_type`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_account
-- ----------------------------
INSERT INTO `ard_user_account` VALUES ('229160943', '0', '0', null);
INSERT INTO `ard_user_account` VALUES ('ddddssss', '0', '9', null);
INSERT INTO `ard_user_account` VALUES ('926467343', '0', '0', null);
INSERT INTO `ard_user_account` VALUES ('dddd', '0', '9', null);

-- ----------------------------
-- Table structure for ard_user_attach
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_attach`;
CREATE TABLE `ard_user_attach` (
  `user_id` varchar(30) NOT NULL,
  `tel_num` varchar(20) NOT NULL,
  `thumb_url` varchar(255) DEFAULT NULL,
  `headpic_url` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tel_num`),
  UNIQUE KEY `telnumber` (`tel_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (tel_num)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_attach
-- ----------------------------
INSERT INTO `ard_user_attach` VALUES ('450190.4826065154', '110', '固定值', 'ffafasf', '2017-11-09 14:44:38', '2017-11-15 16:42:31');
INSERT INTO `ard_user_attach` VALUES ('325753.3576613184', '18201410900', '固定值', '固定值', '2017-11-09 13:53:03', null);
INSERT INTO `ard_user_attach` VALUES ('229160943', '13011836133', '固定值', '固定值', '2017-11-28 15:27:24', null);
INSERT INTO `ard_user_attach` VALUES ('926467343', '18801452756', '固定值', '固定值', '2017-12-26 18:20:52', null);

-- ----------------------------
-- Table structure for ard_user_bm
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_bm`;
CREATE TABLE `ard_user_bm` (
  `user_id` varchar(30) NOT NULL,
  `username` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (username)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_bm
-- ----------------------------
INSERT INTO `ard_user_bm` VALUES ('450190.4826065154', 'zx', '2017-11-09 14:44:38', null);
INSERT INTO `ard_user_bm` VALUES ('325753.3576613184', 'lpf', '2017-11-09 13:53:03', null);
INSERT INTO `ard_user_bm` VALUES ('229160943', '呵呵哒', '2017-11-28 15:27:24', null);
INSERT INTO `ard_user_bm` VALUES ('926467343', 'fffffff', '2017-12-26 18:20:52', null);

-- ----------------------------
-- Table structure for ard_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_role`;
CREATE TABLE `ard_user_role` (
  `user_id` varchar(30) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_role
-- ----------------------------
INSERT INTO `ard_user_role` VALUES ('229160943', '0', '2017-11-28 15:27:24', null);
INSERT INTO `ard_user_role` VALUES ('450190.4826065154', '0', '2017-11-09 14:44:38', null);
INSERT INTO `ard_user_role` VALUES ('926467343', '0', '2017-12-26 18:20:52', null);
INSERT INTO `ard_user_role` VALUES ('325753.3576613184', '0', '2017-11-09 13:53:03', null);

-- ----------------------------
-- Table structure for ard_video
-- ----------------------------
DROP TABLE IF EXISTS `ard_video`;
CREATE TABLE `ard_video` (
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `videoUrl` varchar(512) DEFAULT NULL,
  KEY `type` (`type`) USING BTREE,
  KEY `address` (`address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_video
-- ----------------------------

-- ----------------------------
-- Table structure for tst
-- ----------------------------
DROP TABLE IF EXISTS `tst`;
CREATE TABLE `tst` (
  `id` varchar(255) NOT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tst
-- ----------------------------

-- ----------------------------
-- View structure for ard_news_-1
-- ----------------------------
DROP VIEW IF EXISTS `ard_news_-1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`172.%.%.%` SQL SECURITY DEFINER VIEW `ard_news_-1` AS select `ard`.`ard_news_0`.`id` AS `id`,`ard`.`ard_news_0`.`source` AS `source`,`ard`.`ard_news_0`.`title` AS `title`,`ard`.`ard_news_0`.`content` AS `content`,`ard`.`ard_news_0`.`publishtime` AS `publishtime` from `ard_news_0` union select `ard`.`ard_news_1`.`id` AS `id`,`ard`.`ard_news_1`.`source` AS `source`,`ard`.`ard_news_1`.`title` AS `title`,`ard`.`ard_news_1`.`content` AS `content`,`ard`.`ard_news_1`.`publishtime` AS `publishtime` from `ard_news_1` union select `ard`.`ard_news_2`.`id` AS `id`,`ard`.`ard_news_2`.`source` AS `source`,`ard`.`ard_news_2`.`title` AS `title`,`ard`.`ard_news_2`.`content` AS `content`,`ard`.`ard_news_2`.`publishtime` AS `publishtime` from `ard_news_2` union select `ard`.`ard_news_3`.`id` AS `id`,`ard`.`ard_news_3`.`source` AS `source`,`ard`.`ard_news_3`.`title` AS `title`,`ard`.`ard_news_3`.`content` AS `content`,`ard`.`ard_news_3`.`publishtime` AS `publishtime` from `ard_news_3` ;
