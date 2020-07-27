/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : shop

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 23/07/2020 21:29:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '类别表id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类别名',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '女装', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:01');
INSERT INTO `t_category` VALUES (2, '童装', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:02');
INSERT INTO `t_category` VALUES (3, '羊毛衫', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:03');
INSERT INTO `t_category` VALUES (4, '高端定制', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:03');
INSERT INTO `t_category` VALUES (5, '男鞋', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:04');
INSERT INTO `t_category` VALUES (6, '风衣', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:04');
INSERT INTO `t_category` VALUES (7, '裙子', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:06');
INSERT INTO `t_category` VALUES (8, '女鞋', 'admin', '2019-09-18 09:50:33', 'admin', '2019-09-18 09:56:07');
INSERT INTO `t_category` VALUES (13, '裤子', 'admin', '2019-09-18 02:53:02', 'admin', '2019-09-18 02:53:02');
INSERT INTO `t_category` VALUES (14, '民族风', 'admin', '2019-09-18 02:53:23', 'admin', '2019-09-18 02:53:23');
INSERT INTO `t_category` VALUES (15, '男装', 'admin', '2019-09-18 02:53:37', 'admin', '2019-09-18 02:53:37');
INSERT INTO `t_category` VALUES (16, '父母装', 'admin', '2019-09-18 02:54:35', 'admin', '2019-09-18 02:54:35');

SET FOREIGN_KEY_CHECKS = 1;
