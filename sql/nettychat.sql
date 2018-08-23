/*
Navicat MySQL Data Transfer

Source Server         : mypc
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : nettychat

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-08-23 10:32:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'Myself', '123456', '2018-08-14 19:47:49', '2018-08-14 19:47:49');
INSERT INTO `user` VALUES ('3', 'Chen', '123456abc', '2018-08-20 16:31:49', '2018-08-20 16:31:49');

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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_msg
-- ----------------------------
INSERT INTO `user_msg` VALUES ('8', 'Myself', '你好呀', '2018-08-20 17:26:58', '2018-08-20 17:26:58');
INSERT INTO `user_msg` VALUES ('9', 'Myself', '你是谁？', '2018-08-20 17:27:13', '2018-08-20 17:27:13');
INSERT INTO `user_msg` VALUES ('10', 'Myself', '在吗？', '2018-08-21 17:54:12', '2018-08-21 17:54:12');
INSERT INTO `user_msg` VALUES ('11', 'Chen', '嗯呢', '2018-08-21 17:54:12', '2018-08-21 17:54:12');
INSERT INTO `user_msg` VALUES ('13', 'Myself', 'yo', '2018-08-21 18:01:26', '2018-08-21 18:01:26');
INSERT INTO `user_msg` VALUES ('14', 'Myself', '你好', '2018-08-22 16:24:22', '2018-08-22 16:24:22');
INSERT INTO `user_msg` VALUES ('30', 'Myself', '你好呀！', '2018-08-22 17:03:42', '2018-08-22 17:03:42');
INSERT INTO `user_msg` VALUES ('31', 'Myself', '我很好！', '2018-08-22 17:03:42', '2018-08-22 17:03:42');
INSERT INTO `user_msg` VALUES ('32', 'Myself', '哈哈哈哈', '2018-08-22 17:11:56', '2018-08-22 17:11:56');
INSERT INTO `user_msg` VALUES ('33', 'Myself', '哇哈哈哈哈', '2018-08-22 17:11:56', '2018-08-22 17:11:56');
INSERT INTO `user_msg` VALUES ('34', 'Myself', 'asdf', '2018-08-22 17:22:19', '2018-08-22 17:22:19');
INSERT INTO `user_msg` VALUES ('35', 'Myself', '厉害 厉害', '2018-08-22 17:23:20', '2018-08-22 17:23:20');
INSERT INTO `user_msg` VALUES ('36', 'Myself', '哈哈', '2018-08-22 17:23:43', '2018-08-22 17:23:43');
INSERT INTO `user_msg` VALUES ('37', 'Myself', '你好！', '2018-08-23 10:19:14', '2018-08-23 10:19:14');
INSERT INTO `user_msg` VALUES ('38', 'Chen', '收到。', '2018-08-23 10:19:15', '2018-08-23 10:19:15');
INSERT INTO `user_msg` VALUES ('39', 'Myself', '前端框架用Vue？', '2018-08-23 10:19:15', '2018-08-23 10:19:15');
INSERT INTO `user_msg` VALUES ('40', 'Chen', '可以试试', '2018-08-23 10:19:15', '2018-08-23 10:19:15');
INSERT INTO `user_msg` VALUES ('41', 'Myself', '下版再加一些新的功能', '2018-08-23 10:19:15', '2018-08-23 10:19:15');
INSERT INTO `user_msg` VALUES ('42', 'Chen', 'okay', '2018-08-23 10:19:15', '2018-08-23 10:19:15');
