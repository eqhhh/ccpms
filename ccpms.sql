/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : ccpms

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 10/06/2022 09:52:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for charging_order
-- ----------------------------
DROP TABLE IF EXISTS `charging_order`;
CREATE TABLE `charging_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT 'user id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `pile_id` int(11) NULL DEFAULT NULL COMMENT '充电所在充电桩id',
  `pile_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充电所在充电桩',
  `station_id` int(11) NULL DEFAULT NULL COMMENT '充电所在充电站id',
  `station_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充电所在充电站',
  `pay_channel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付渠道',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '已完成' COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of charging_order
-- ----------------------------
INSERT INTO `charging_order` VALUES (11, 11, 'eqadmin', '2022-05-17 13:32:16', '2022-05-18 13:32:18', 1, '东一1号桩', 1, '东一', '账户', NULL, 5.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (12, 11, 'eqadmin', '2022-05-22 01:31:39', '2022-05-23 09:17:13', 1, '东一1号桩', 1, '东一', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (13, 11, 'eqadmin', '2022-05-22 01:31:47', '2022-05-23 09:17:16', 2, '东一2号桩', 1, '东一', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (14, 11, 'eqadmin', '2022-05-23 09:32:59', '2022-05-26 20:03:05', 1, '东一1号桩', 1, '东一', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (15, 12, 'admin', '2022-05-26 17:42:45', '2022-05-26 17:43:10', 5, '家和1号桩', 12, '家和', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (16, 12, 'admin', '2022-05-26 17:43:16', '2022-05-26 17:43:20', 6, '家和2号桩', 12, '家和', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (17, 11, 'eqadmin', '2022-05-26 20:03:24', '2022-05-27 12:58:00', 7, '东四1号桩', 4, '东四', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (18, 11, 'eqadmin', '2022-06-01 15:19:03', '2022-06-05 18:35:12', 1, '东一1号桩', 1, '东一', NULL, NULL, NULL, '已完成', NULL);
INSERT INTO `charging_order` VALUES (19, 11, 'eqadmin', '2022-06-05 18:34:47', NULL, 2, '东一2号桩', 1, '东一', NULL, NULL, NULL, '充电中', NULL);
INSERT INTO `charging_order` VALUES (23, 13, 'eqhhh', '2022-06-06 03:12:18', '2022-06-06 03:14:05', 1, '东一1号桩', 1, '东一', '账户支付', NULL, 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (24, 13, 'eqhhh', '2022-06-06 10:27:27', '2022-06-06 11:43:01', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 03:23:08', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (25, 13, 'eqhhh', '2022-06-06 10:27:30', '2022-06-06 11:43:03', 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-06 03:37:45', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (26, 13, 'eqhhh', '2022-06-06 03:42:18', '2022-06-06 03:44:43', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 03:42:15', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (27, 13, 'eqhhh', '2022-06-06 03:46:21', '2022-06-06 03:46:30', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 03:45:19', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (28, 13, 'eqhhh', '2022-06-06 10:31:34', '2022-06-06 10:31:37', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 10:31:30', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (29, 13, 'eqhhh', '2022-06-06 11:34:26', '2022-06-06 11:34:29', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 11:01:46', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (30, 13, 'eqhhh', '2022-06-06 11:45:30', '2022-06-06 11:45:34', 5, '家和1号桩', 12, '家和', '账户支付', '2022-06-06 11:06:57', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (31, 13, 'eqhhh', '2022-06-06 11:45:36', '2022-06-06 11:45:39', 6, '家和2号桩', 12, '家和', '账户支付', '2022-06-06 11:33:55', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (32, 13, 'eqhhh', '2022-06-06 11:45:45', '2022-06-06 11:45:48', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 11:36:09', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (33, 13, 'eqhhh', '2022-06-06 11:46:05', '2022-06-06 11:46:07', 7, '东四1号桩', 4, '东四', '账户支付', '2022-06-06 11:40:45', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (34, 13, 'eqhhh', '2022-06-06 11:46:00', '2022-06-06 11:46:03', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 11:43:09', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (35, 13, 'eqhhh', '2022-06-06 11:45:55', '2022-06-06 11:45:58', 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-06 11:45:25', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (36, 13, 'eqhhh', '2022-06-06 12:27:13', '2022-06-06 12:27:27', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 11:51:01', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (37, 13, 'eqhhh', '2022-06-06 12:27:16', '2022-06-06 12:27:29', 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-06 11:53:11', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (38, 13, 'eqhhh', '2022-06-06 12:27:17', '2022-06-06 12:27:31', 4, '东二1号桩', 2, '东二', '账户支付', '2022-06-06 11:54:06', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (39, 13, 'eqhhh', '2022-06-06 12:27:19', '2022-06-06 12:27:33', 5, '家和1号桩', 12, '家和', '账户支付', '2022-06-06 11:58:18', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (40, 13, 'eqhhh', '2022-06-06 12:27:22', '2022-06-06 12:27:35', 6, '家和2号桩', 12, '家和', '账户支付', '2022-06-06 12:23:31', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (41, 13, 'eqhhh', '2022-06-06 12:53:51', '2022-06-06 12:53:54', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 12:41:10', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (42, 13, 'eqhhh', '2022-06-06 12:53:56', '2022-06-06 12:53:59', 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-06 12:51:24', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (43, 13, 'eqhhh', '2022-06-06 12:54:40', '2022-06-06 12:57:00', 3, '东一3号桩', 1, '东一', '微信支付', '2022-06-06 12:54:28', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (44, 13, 'eqhhh', '2022-06-06 12:57:03', '2022-06-06 12:59:07', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 12:56:53', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (45, 13, 'eqhhh', '2022-06-06 12:59:11', '2022-06-06 12:59:31', 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-06 12:59:00', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (46, 13, 'eqhhh', '2022-06-06 15:02:00', '2022-06-06 15:02:02', 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 15:01:52', 3.00, '已完成', NULL);
INSERT INTO `charging_order` VALUES (47, 13, 'eqhhh', '2022-06-06 21:04:01', NULL, 1, '东一1号桩', 1, '东一', '账户支付', '2022-06-06 21:02:05', 3.00, '充电中', NULL);
INSERT INTO `charging_order` VALUES (48, 15, 'user1', NULL, NULL, 3, '东一3号桩', 1, '东一', '账户支付', '2022-06-07 08:28:36', 3.00, '已预约', NULL);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源路径',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'Home', '/home', '主页', 'House');
INSERT INTO `permission` VALUES (2, 'User', '/user', '用户管理', 'User');
INSERT INTO `permission` VALUES (3, 'Permission', '/permisssion', '权限菜单', 'Unlock');
INSERT INTO `permission` VALUES (4, 'Role', '/role', '角色管理', 'UserFilled');
INSERT INTO `permission` VALUES (5, 'Person', '/person', '个人信息', '');
INSERT INTO `permission` VALUES (6, 'Password', '/password', '修改密码', NULL);
INSERT INTO `permission` VALUES (7, 'Station', '/station', '充电站管理', 'OfficeBuilding');
INSERT INTO `permission` VALUES (8, 'Pile', '/pile', '充电桩管理', 'Lightning');
INSERT INTO `permission` VALUES (9, 'Order', '/order', '订单管理', 'Tickets');
INSERT INTO `permission` VALUES (11, 'QueryPile', '/charge/query', '充电桩定位', 'Apple');
INSERT INTO `permission` VALUES (12, 'UsePile', '/charge/use', '充电', NULL);
INSERT INTO `permission` VALUES (13, 'Statistics', '/statistics', '数据统计', 'Histogram');

-- ----------------------------
-- Table structure for pile
-- ----------------------------
DROP TABLE IF EXISTS `pile`;
CREATE TABLE `pile`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pile_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '充电桩名',
  `station_id` int(11) NULL DEFAULT NULL COMMENT '所属充电站id',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '空闲' COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充电桩信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pile
-- ----------------------------
INSERT INTO `pile` VALUES (1, '东一1号桩', 1, '充电中', NULL);
INSERT INTO `pile` VALUES (2, '东一2号桩', 1, '充电中', NULL);
INSERT INTO `pile` VALUES (3, '东一3号桩', 1, '已预约', NULL);
INSERT INTO `pile` VALUES (4, '东二1号桩', 2, '空闲', NULL);
INSERT INTO `pile` VALUES (5, '家和1号桩', 12, '空闲', NULL);
INSERT INTO `pile` VALUES (6, '家和2号桩', 12, '空闲', NULL);
INSERT INTO `pile` VALUES (7, '东四1号桩', 4, '空闲', NULL);
INSERT INTO `pile` VALUES (8, '东三1号桩', 3, '维修中', NULL);
INSERT INTO `pile` VALUES (9, '家和3号桩', 12, '空闲', NULL);
INSERT INTO `pile` VALUES (10, '养贤1号桩', 13, '空闲', NULL);
INSERT INTO `pile` VALUES (11, '养贤2号桩', 13, '空闲', NULL);
INSERT INTO `pile` VALUES (12, '养贤3号桩', 13, '空闲', NULL);
INSERT INTO `pile` VALUES (13, '计算机楼1号桩', 16, '空闲', NULL);
INSERT INTO `pile` VALUES (14, '计算机楼2号桩', 16, '空闲', NULL);
INSERT INTO `pile` VALUES (15, '计算机楼3号桩', 16, '空闲', NULL);
INSERT INTO `pile` VALUES (16, '计算机楼4号桩', 16, '空闲', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员');
INSERT INTO `role` VALUES (2, 'user', '普通用户');
INSERT INTO `role` VALUES (11, 'admin11', '超级管理员');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 7);
INSERT INTO `role_permission` VALUES (1, 8);
INSERT INTO `role_permission` VALUES (1, 9);
INSERT INTO `role_permission` VALUES (1, 13);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 5);
INSERT INTO `role_permission` VALUES (2, 6);
INSERT INTO `role_permission` VALUES (2, 11);
INSERT INTO `role_permission` VALUES (2, 12);
INSERT INTO `role_permission` VALUES (11, 1);
INSERT INTO `role_permission` VALUES (11, 2);
INSERT INTO `role_permission` VALUES (11, 3);
INSERT INTO `role_permission` VALUES (11, 4);
INSERT INTO `role_permission` VALUES (11, 5);
INSERT INTO `role_permission` VALUES (11, 6);
INSERT INTO `role_permission` VALUES (11, 7);
INSERT INTO `role_permission` VALUES (11, 8);
INSERT INTO `role_permission` VALUES (11, 9);
INSERT INTO `role_permission` VALUES (11, 11);
INSERT INTO `role_permission` VALUES (11, 12);
INSERT INTO `role_permission` VALUES (11, 13);

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `station_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '未知站名' COMMENT '充电站名',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '运行中' COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充电站信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of station
-- ----------------------------
INSERT INTO `station` VALUES (1, '东一', '运行中', NULL);
INSERT INTO `station` VALUES (2, '东二', '运行中', NULL);
INSERT INTO `station` VALUES (3, '东三', '维修中', NULL);
INSERT INTO `station` VALUES (4, '东四', '运行中', NULL);
INSERT INTO `station` VALUES (5, '东十四', '运行中', NULL);
INSERT INTO `station` VALUES (6, '西一', '运行中', NULL);
INSERT INTO `station` VALUES (7, '西二', '运行中', NULL);
INSERT INTO `station` VALUES (8, '西三', '运行中', NULL);
INSERT INTO `station` VALUES (10, '西八', '运行中', NULL);
INSERT INTO `station` VALUES (11, '图书馆', '运行中', NULL);
INSERT INTO `station` VALUES (12, '家和', '运行中', NULL);
INSERT INTO `station` VALUES (13, '养贤', '运行中', NULL);
INSERT INTO `station` VALUES (14, '健行楼', '运行中', NULL);
INSERT INTO `station` VALUES (15, '广知楼', '运行中', NULL);
INSERT INTO `station` VALUES (16, '计算机楼', '运行中', NULL);
INSERT INTO `station` VALUES (17, '艺术楼', '运行中', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `limit_money` decimal(10, 2) NULL DEFAULT 3.00 COMMENT '充电限额',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '状态',
  `account` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (11, 'eqadmin', '$2a$10$CU4k9iMK7B6geOKIrpfLtuT1g.AAgv4qC7bTfxw3tTFsZVxgamhBW', 'eq', 3.00, '男', '20', '家和东苑14', '19850211', '482421656@qq.com', '正常', 14400.20, NULL, NULL);
INSERT INTO `user` VALUES (12, 'admin', '$2a$10$hDyjYt2rqUlz2qCIpaCX..AgE.yVGDMIYaDDD6TaPdhDmxAbY5wbG', '蓝天', 3.00, '男', '21', '家和东苑13', NULL, NULL, '正常', 0.00, NULL, NULL);
INSERT INTO `user` VALUES (13, 'eqhhh', '$2a$10$RKB2.6W6CR/DsHcfL9Nseen.o8/fmVdGH1RMpErAUhGDD0GL1OO72', '白云', 3.00, '女', '22', '家和西苑8', NULL, NULL, '正常', 143.00, NULL, NULL);
INSERT INTO `user` VALUES (14, 'testuser', '$2a$10$WF2Yfh5zUY3qfnQ41l5Vi.WswUCAbU7PNPZ3RNxQazd0DjOAXd1AC', '用户1528415273152925697', 3.00, NULL, NULL, '家和东苑14', NULL, NULL, '正常', 0.00, NULL, NULL);
INSERT INTO `user` VALUES (15, 'user1', '$2a$10$jO.PCZBiYVZTMbgu1qOZg.y1Tc0Wx1Mo76LQUT8mBpMeauwwLOz22', '用户1', 3.00, '男', NULL, NULL, NULL, NULL, '正常', 17.56, NULL, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (11, 11);
INSERT INTO `user_role` VALUES (12, 1);
INSERT INTO `user_role` VALUES (13, 2);
INSERT INTO `user_role` VALUES (14, 2);
INSERT INTO `user_role` VALUES (15, 2);

SET FOREIGN_KEY_CHECKS = 1;
