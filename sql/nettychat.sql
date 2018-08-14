/*
Navicat MySQL Data Transfer

Source Server         : mypc
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : nettychat

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-14 15:55:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_msg
-- ----------------------------
DROP TABLE IF EXISTS `user_msg`;
CREATE TABLE `user_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_msg
-- ----------------------------
INSERT INTO `user_msg` VALUES ('1', '亪', '今天不开心', '2018-08-14 14:26:02', '2018-08-14 14:26:02');
INSERT INTO `user_msg` VALUES ('2', '祐', '不错呀', '2018-08-14 15:09:40', '2018-08-14 15:09:40');
INSERT INTO `user_msg` VALUES ('3', '搈', '开心 开心', '2018-08-14 15:09:40', '2018-08-14 15:09:40');
INSERT INTO `user_msg` VALUES ('4', '兇', '可以的，后面再做个深入一点的', '2018-08-14 15:18:35', '2018-08-14 15:18:35');
INSERT INTO `user_msg` VALUES ('5', '倎', '开源这个项目', '2018-08-14 15:18:35', '2018-08-14 15:18:35');
INSERT INTO `user_msg` VALUES ('6', '蝡', '1-someting', '2018-08-14 15:24:28', '2018-08-14 15:24:28');
INSERT INTO `user_msg` VALUES ('7', '弔', '不行呀', '2018-08-14 15:24:29', '2018-08-14 15:24:29');
INSERT INTO `user_msg` VALUES ('8', '習', '可以的', '2018-08-14 15:26:03', '2018-08-14 15:26:03');
INSERT INTO `user_msg` VALUES ('9', '蔫', '开源这个项目', '2018-08-14 15:26:03', '2018-08-14 15:26:03');
