/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 192.168.163.130:3306
 Source Schema         : sentinel

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 26/02/2020 17:20:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sentinel_user
-- ----------------------------
DROP TABLE IF EXISTS `sentinel_user`;
CREATE TABLE `sentinel_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `state` int(1) NOT NULL COMMENT '状态：0|启用，1|停用，2|删除',
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sentinel_user
-- ----------------------------
INSERT INTO `sentinel_user` VALUES (1001, 'cosmax', '$2a$10$wzYJNcEEm6VqK5/JAoyRWOcTP8F.l364rwqo8DEMvmXKoxOaws/He', '2020-02-26 14:33:43', '2020-02-26 14:33:48', 0);
INSERT INTO `sentinel_user` VALUES (1002, 'sentinel', '$2a$10$n6WPtPwp3prNbIHsEQaGhuq5Iy2ROsrTDtmVriHYo0eOrYTtXROy6', '2020-02-26 17:18:37', '2020-02-26 17:18:34', 0);

SET FOREIGN_KEY_CHECKS = 1;
