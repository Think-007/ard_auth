/*
Navicat MySQL Data Transfer

Source Server         : 172.18.5.110
Source Server Version : 50525
Source Host           : 172.18.5.110:3306
Source Database       : ard

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-04-10 20:09:04
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
  `picUrl` varchar(512) DEFAULT NULL,
  UNIQUE KEY `name` (`name`),
  KEY `type` (`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_easylife
-- ----------------------------
INSERT INTO `ard_easylife` VALUES ('dddd', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd1', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd2', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee3', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff4', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss5', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt6', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd11', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd22', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee33', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff44', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss55', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt66', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd9', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd9', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee9', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff9', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss9', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt9', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd19', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd29', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee39', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff49', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss59', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt69', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd119', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd229', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee339', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff449', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss559', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt669', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd91', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd92', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee93', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff94', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss95', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt96', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd197', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd298', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee399', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff493', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss594', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt694', '6', 'wwww.git.com', null);
INSERT INTO `ard_easylife` VALUES ('dddd1194', '3', 'www.baidu.co', null);
INSERT INTO `ard_easylife` VALUES ('qwd2294', '4', 'www.baidu.com', null);
INSERT INTO `ard_easylife` VALUES ('eeee3394', '3', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ffff4494', '4', 'wwww.baidu.com', '');
INSERT INTO `ard_easylife` VALUES ('ssss5594', '5', 'www.google.com', null);
INSERT INTO `ard_easylife` VALUES ('tttt6694', '6', 'wwww.git.com', null);

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
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  KEY `role_id` (`role_id`),
  CONSTRAINT `ard_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `ard_role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for ard_type
-- ----------------------------
DROP TABLE IF EXISTS `ard_type`;
CREATE TABLE `ard_type` (
  `biz_id` int(11) NOT NULL,
  `type_id` int(255) NOT NULL,
  `type_name` varchar(10) NOT NULL,
  `picUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`biz_id`,`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ard_type
-- ----------------------------
INSERT INTO `ard_type` VALUES ('0', '3', '新闻', '33333');
INSERT INTO `ard_type` VALUES ('0', '4', '游戏', '99999999');
INSERT INTO `ard_type` VALUES ('0', '5', '体育', '88888888888');
INSERT INTO `ard_type` VALUES ('1', '1', '北京', '00000000000');
INSERT INTO `ard_type` VALUES ('1', '3', '唐山', 'jfioqofjiewfi');
INSERT INTO `ard_type` VALUES ('1', '4', '内蒙', '32423423');
INSERT INTO `ard_type` VALUES ('0', '6', '综艺', 'oooioio');

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
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user
-- ----------------------------
INSERT INTO `ard_user` VALUES ('468720394', '14e767a0fe9cab6270d5d56ac8aa6600', '0e832684f361e1491ed91e5ee835c187', '0', '0', '0', '2018-04-10 19:44:59', '0000-00-00 00:00:00');

-- ----------------------------
-- Table structure for ard_user_account
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_account`;
CREATE TABLE `ard_user_account` (
  `user_id` varchar(30) NOT NULL,
  `account_type` tinyint(4) NOT NULL,
  `balance` double DEFAULT NULL,
  `updatetime` timestamp NOT NULL DEFAULT '2018-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`account_type`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_account
-- ----------------------------
INSERT INTO `ard_user_account` VALUES ('468720394', '0', '15', '2018-04-10 20:14:27');

-- ----------------------------
-- Table structure for ard_user_attach
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_attach`;
CREATE TABLE `ard_user_attach` (
  `user_id` varchar(30) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `tel_num` varchar(20) NOT NULL,
  `thumb_url` varchar(255) DEFAULT NULL,
  `headpic_url` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `main_attach` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`tel_num`),
  UNIQUE KEY `telnumber` (`tel_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (tel_num)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_attach
-- ----------------------------
INSERT INTO `ard_user_attach` VALUES ('468720394', null, '123456789', 'headUrlheadUrlheadUrl', 'headUrlheadUrlheadUrl', '2018-04-10 19:44:59', '0000-00-00 00:00:00', '1');

-- ----------------------------
-- Table structure for ard_user_bm
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_bm`;
CREATE TABLE `ard_user_bm` (
  `user_id` varchar(30) NOT NULL,
  `username` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (username)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_bm
-- ----------------------------
INSERT INTO `ard_user_bm` VALUES ('468720394', '呵呵呵7800.', '2018-04-10 19:44:59', '0000-00-00 00:00:00');

-- ----------------------------
-- Table structure for ard_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ard_user_role`;
CREATE TABLE `ard_user_role` (
  `user_id` varchar(30) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*!50100 PARTITION BY KEY (user_id)
PARTITIONS 10 */;

-- ----------------------------
-- Records of ard_user_role
-- ----------------------------
INSERT INTO `ard_user_role` VALUES ('468720394', '0', '2018-04-10 19:44:59', '0000-00-00 00:00:00');

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
INSERT INTO `ard_video` VALUES ('北京值声', '1', '爆头', 'afasfafaf');
INSERT INTO `ard_video` VALUES ('唐哈值声', '3', '可爱', '890890890890890890');

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
