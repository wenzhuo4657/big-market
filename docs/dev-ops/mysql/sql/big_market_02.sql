/*
 Navicat Premium Data Transfer

 Source Server         : 京东云3306
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : 117.72.36.124:3306
 Source Schema         : big_market_02

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 24/12/2024 15:44:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE database if NOT EXISTS `big_market_02` default character set utf8mb4;
use `big_market_02`;

-- ----------------------------
-- Table structure for raffle_activity_account
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account`;
CREATE TABLE `raffle_activity_account`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `total_count` int NOT NULL COMMENT '总次数',
  `total_count_surplus` int NOT NULL COMMENT '总次数-剩余',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------
INSERT INTO `raffle_activity_account` VALUES (8, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 200, 190, 200, 190, 200, 190, '2024-12-24 14:16:55', '2024-12-24 14:54:46');

-- ----------------------------
-- Table structure for raffle_activity_account_day
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_day`;
CREATE TABLE `raffle_activity_account_day`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `day` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日期（yyyy-mm-dd）',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_day`(`user_id` ASC, `activity_id` ASC, `day` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表-日次数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_account_day
-- ----------------------------
INSERT INTO `raffle_activity_account_day` VALUES (6, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '2024-12-24', 200, 190, '2024-12-24 14:19:31', '2024-12-24 14:54:46');

-- ----------------------------
-- Table structure for raffle_activity_account_month
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_month`;
CREATE TABLE `raffle_activity_account_month`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '月（yyyy-mm）',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_month`(`user_id` ASC, `activity_id` ASC, `month` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表-月次数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_account_month
-- ----------------------------
INSERT INTO `raffle_activity_account_month` VALUES (4, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '2024-12', 200, 190, '2024-12-24 14:19:31', '2024-12-24 14:54:46');

-- ----------------------------
-- Table structure for raffle_activity_order_000
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_000`;
CREATE TABLE `raffle_activity_order_000`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order_001
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_001`;
CREATE TABLE `raffle_activity_order_001`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 261 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_order_001
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order_002
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_002`;
CREATE TABLE `raffle_activity_order_002`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_order_002
-- ----------------------------

-- ----------------------------
-- Table structure for raffle_activity_order_003
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_003`;
CREATE TABLE `raffle_activity_order_003`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of raffle_activity_order_003
-- ----------------------------
INSERT INTO `raffle_activity_order_003` VALUES (5, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 9012, 100401, '测试活动', 100006, '800951235771', '2024-12-24 06:16:37', 100, 100, 100, 1.68, 'completed', 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141626', '2024-12-24 14:16:37', '2024-12-24 14:16:54');
INSERT INTO `raffle_activity_order_003` VALUES (6, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 9012, 100401, '测试活动', 100006, '032786790385', '2024-12-24 06:16:37', 100, 100, 100, 1.68, 'wait_pay', 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141616', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `raffle_activity_order_003` VALUES (7, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 9012, 100401, '测试活动', 100006, '999087099011', '2024-12-24 06:16:37', 100, 100, 100, 1.68, 'completed', 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141621', '2024-12-24 14:16:38', '2024-12-24 14:34:42');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `topic` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息主题',
  `message_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息编号',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息主体',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '任务状态；create-创建、completed-完成、fail-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_message_id`(`message_id` ASC) USING BTREE,
  INDEX `idx_state`(`state` ASC) USING BTREE,
  INDEX `idx_create_time`(`update_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务表，发送MQ' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (38, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_rebate', '99498167816', '{\"data\":{\"bizId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s_integral_20241224\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"99498167816\",\"timestamp\":1735019294575}', 'completed', '2024-12-24 13:48:15', '2024-12-24 14:15:05');
INSERT INTO `task` VALUES (39, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'credit_adjust_success', '34757079434', '{\"data\":{\"amount\":1.68,\"orderId\":\"334448296845\",\"outBusinessNo\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141626\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"34757079434\",\"timestamp\":1735020996954}', 'completed', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `task` VALUES (40, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'credit_adjust_success', '75668599968', '{\"data\":{\"amount\":1.68,\"orderId\":\"362010005185\",\"outBusinessNo\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141616\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"75668599968\",\"timestamp\":1735020996985}', 'completed', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `task` VALUES (41, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'credit_adjust_success', '64286596844', '{\"data\":{\"amount\":1.68,\"orderId\":\"889418261998\",\"outBusinessNo\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141621\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"64286596844\",\"timestamp\":1735020996989}', 'completed', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `task` VALUES (42, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '20343833547', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"249811317277\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"20343833547\",\"timestamp\":1735021170558}', 'completed', '2024-12-24 14:19:31', '2024-12-24 14:19:31');
INSERT INTO `task` VALUES (43, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '16061132911', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"224518754870\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"16061132911\",\"timestamp\":1735021226226}', 'completed', '2024-12-24 14:20:27', '2024-12-24 14:20:27');
INSERT INTO `task` VALUES (44, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '48120206035', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"883767896100\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"48120206035\",\"timestamp\":1735021680884}', 'completed', '2024-12-24 14:28:02', '2024-12-24 14:28:02');
INSERT INTO `task` VALUES (45, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '43702516923', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"337752675998\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"43702516923\",\"timestamp\":1735022337340}', 'completed', '2024-12-24 14:38:58', '2024-12-24 14:38:59');
INSERT INTO `task` VALUES (46, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '19668958319', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"300675366228\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"19668958319\",\"timestamp\":1735022379845}', 'completed', '2024-12-24 14:39:41', '2024-12-24 14:39:41');
INSERT INTO `task` VALUES (47, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '85226707142', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"391697407572\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"85226707142\",\"timestamp\":1735022439652}', 'completed', '2024-12-24 14:40:40', '2024-12-24 14:40:40');
INSERT INTO `task` VALUES (48, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '42794225631', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"790960070366\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"42794225631\",\"timestamp\":1735023218473}', 'completed', '2024-12-24 14:53:39', '2024-12-24 14:53:39');
INSERT INTO `task` VALUES (49, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '41279047049', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"168796313597\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"41279047049\",\"timestamp\":1735023267063}', 'completed', '2024-12-24 14:54:28', '2024-12-24 14:54:28');
INSERT INTO `task` VALUES (50, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '92182931043', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"340250951450\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"92182931043\",\"timestamp\":1735023275816}', 'completed', '2024-12-24 14:54:36', '2024-12-24 14:54:37');
INSERT INTO `task` VALUES (51, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 'send_award', '90394868932', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"106820986661\",\"userId\":\"ohGUf67Vme6-oFdR7b1Ml8mw34_s\"},\"id\":\"90394868932\",\"timestamp\":1735023285612}', 'completed', '2024-12-24 14:54:46', '2024-12-24 14:54:46');

-- ----------------------------
-- Table structure for user_award_record_000
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_000`;
CREATE TABLE `user_award_record_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_award_record_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_award_record_001
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_001`;
CREATE TABLE `user_award_record_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_award_record_001
-- ----------------------------

-- ----------------------------
-- Table structure for user_award_record_002
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_002`;
CREATE TABLE `user_award_record_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_award_record_002
-- ----------------------------

-- ----------------------------
-- Table structure for user_award_record_003
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_003`;
CREATE TABLE `user_award_record_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_award_record_003
-- ----------------------------
INSERT INTO `user_award_record_003` VALUES (16, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '249811317277', 101, '随机积分', '2024-12-24 06:19:31', 'completed', '2024-12-24 14:19:31', '2024-12-24 14:52:34');
INSERT INTO `user_award_record_003` VALUES (17, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '224518754870', 101, '随机积分', '2024-12-24 06:20:26', 'completed', '2024-12-24 14:20:27', '2024-12-24 14:52:26');
INSERT INTO `user_award_record_003` VALUES (18, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '883767896100', 101, '随机积分', '2024-12-24 06:28:01', 'completed', '2024-12-24 14:28:02', '2024-12-24 14:52:56');
INSERT INTO `user_award_record_003` VALUES (19, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '337752675998', 101, '随机积分', '2024-12-24 06:38:57', 'completed', '2024-12-24 14:38:58', '2024-12-24 14:39:27');
INSERT INTO `user_award_record_003` VALUES (20, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '300675366228', 101, '随机积分', '2024-12-24 06:39:40', 'create', '2024-12-24 14:39:40', '2024-12-24 14:39:40');
INSERT INTO `user_award_record_003` VALUES (21, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '391697407572', 101, '随机积分', '2024-12-24 06:40:40', 'create', '2024-12-24 14:40:40', '2024-12-24 14:40:40');
INSERT INTO `user_award_record_003` VALUES (22, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '790960070366', 101, '随机积分', '2024-12-24 06:53:38', 'completed', '2024-12-24 14:53:39', '2024-12-24 14:54:06');
INSERT INTO `user_award_record_003` VALUES (23, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '168796313597', 101, '随机积分', '2024-12-24 06:54:27', 'create', '2024-12-24 14:54:28', '2024-12-24 14:54:28');
INSERT INTO `user_award_record_003` VALUES (24, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '340250951450', 101, '随机积分', '2024-12-24 06:54:36', 'create', '2024-12-24 14:54:36', '2024-12-24 14:54:36');
INSERT INTO `user_award_record_003` VALUES (25, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, 100006, '106820986661', 101, '随机积分', '2024-12-24 06:54:46', 'create', '2024-12-24 14:54:46', '2024-12-24 14:54:46');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_000`;
CREATE TABLE `user_behavior_rebate_order_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_behavior_rebate_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_behavior_rebate_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_001`;
CREATE TABLE `user_behavior_rebate_order_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_behavior_rebate_order_001
-- ----------------------------

-- ----------------------------
-- Table structure for user_behavior_rebate_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_002`;
CREATE TABLE `user_behavior_rebate_order_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_behavior_rebate_order_002
-- ----------------------------

-- ----------------------------
-- Table structure for user_behavior_rebate_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_003`;
CREATE TABLE `user_behavior_rebate_order_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_behavior_rebate_order_003
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_003` VALUES (9, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', '960813540506', 'sign', '签到返利-积分', 'integral', '10', '20241224', 'ohGUf67Vme6-oFdR7b1Ml8mw34_s_integral_20241224', '2024-12-24 13:48:15', '2024-12-24 13:48:15');

-- ----------------------------
-- Table structure for user_credit_account
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_account`;
CREATE TABLE `user_credit_account`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '总积分，显示总账户值，记得一个人获得的总积分',
  `available_amount` decimal(10, 2) NOT NULL COMMENT '可用积分，每次扣减的值',
  `account_status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账户状态【open - 可用，close - 冻结】',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_credit_account
-- ----------------------------
INSERT INTO `user_credit_account` VALUES (3, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 247.96, 247.96, 'open', '2024-12-24 13:54:54', '2024-12-24 14:54:06');

-- ----------------------------
-- Table structure for user_credit_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_000`;
CREATE TABLE `user_credit_order_000`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_credit_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_credit_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_001`;
CREATE TABLE `user_credit_order_001`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_credit_order_001
-- ----------------------------

-- ----------------------------
-- Table structure for user_credit_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_002`;
CREATE TABLE `user_credit_order_002`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_credit_order_002
-- ----------------------------

-- ----------------------------
-- Table structure for user_credit_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_003`;
CREATE TABLE `user_credit_order_003`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_credit_order_003
-- ----------------------------
INSERT INTO `user_credit_order_003` VALUES (1, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', '334448296845', '兑换抽奖', 'reverse', 1.68, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141626', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `user_credit_order_003` VALUES (2, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', '362010005185', '兑换抽奖', 'reverse', 1.68, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141616', '2024-12-24 14:16:38', '2024-12-24 14:16:38');
INSERT INTO `user_credit_order_003` VALUES (3, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', '889418261998', '兑换抽奖', 'reverse', 1.68, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s20241224141621', '2024-12-24 14:16:38', '2024-12-24 14:16:38');

-- ----------------------------
-- Table structure for user_raffle_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_000`;
CREATE TABLE `user_raffle_order_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_raffle_order_000
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_001`;
CREATE TABLE `user_raffle_order_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_raffle_order_001
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_002`;
CREATE TABLE `user_raffle_order_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_raffle_order_002
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_003`;
CREATE TABLE `user_raffle_order_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_raffle_order_003
-- ----------------------------
INSERT INTO `user_raffle_order_003` VALUES (16, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '249811317277', '2024-12-24 06:19:30', 'used', '2024-12-24 14:19:31', '2024-12-24 14:19:31');
INSERT INTO `user_raffle_order_003` VALUES (17, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '224518754870', '2024-12-24 06:20:25', 'used', '2024-12-24 14:20:26', '2024-12-24 14:20:27');
INSERT INTO `user_raffle_order_003` VALUES (18, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '883767896100', '2024-12-24 06:26:35', 'used', '2024-12-24 14:26:36', '2024-12-24 14:28:02');
INSERT INTO `user_raffle_order_003` VALUES (19, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '337752675998', '2024-12-24 06:30:56', 'used', '2024-12-24 14:30:57', '2024-12-24 14:38:58');
INSERT INTO `user_raffle_order_003` VALUES (20, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '300675366228', '2024-12-24 06:39:39', 'used', '2024-12-24 14:39:40', '2024-12-24 14:39:41');
INSERT INTO `user_raffle_order_003` VALUES (21, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '391697407572', '2024-12-24 06:40:39', 'used', '2024-12-24 14:40:40', '2024-12-24 14:40:40');
INSERT INTO `user_raffle_order_003` VALUES (22, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '790960070366', '2024-12-24 06:53:33', 'used', '2024-12-24 14:53:39', '2024-12-24 14:53:39');
INSERT INTO `user_raffle_order_003` VALUES (23, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '168796313597', '2024-12-24 06:54:26', 'used', '2024-12-24 14:54:27', '2024-12-24 14:54:28');
INSERT INTO `user_raffle_order_003` VALUES (24, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '340250951450', '2024-12-24 06:54:35', 'used', '2024-12-24 14:54:36', '2024-12-24 14:54:37');
INSERT INTO `user_raffle_order_003` VALUES (25, 'ohGUf67Vme6-oFdR7b1Ml8mw34_s', 100401, '测试活动', 100006, '106820986661', '2024-12-24 06:54:45', 'used', '2024-12-24 14:54:46', '2024-12-24 14:54:46');

SET FOREIGN_KEY_CHECKS = 1;
