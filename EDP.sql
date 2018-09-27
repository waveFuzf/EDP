/*
Navicat MySQL Data Transfer

Source Server         : ZJKJ
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : expressdeliveryplatform

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2018-09-27 17:44:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate` (
  `evaluateId` int(11) NOT NULL AUTO_INCREMENT,
  `evaluate` varchar(255) DEFAULT NULL,
  `evaluate_date` datetime DEFAULT NULL,
  `star_level` double DEFAULT NULL,
  `evaluated_id` int(11) DEFAULT NULL,
  `evaluater_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`evaluateId`),
  KEY `FK_5i5o2pj79f55srg277t80k2nn` (`evaluated_id`),
  KEY `FK_6uavtvgtg5h6xhgd08gqbfbqa` (`evaluater_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of evaluate
-- ----------------------------
INSERT INTO `evaluate` VALUES ('1', '服务NICE！', '2018-09-27 14:47:17', '4', '1', '2');

-- ----------------------------
-- Table structure for express
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `expressId` int(11) NOT NULL AUTO_INCREMENT,
  `size` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `tip` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `publishId` int(11) DEFAULT NULL,
  PRIMARY KEY (`expressId`),
  KEY `FK_ew6j3nth1h4kwdrasp28s221k` (`publishId`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of express
-- ----------------------------
INSERT INTO `express` VALUES ('1', '2', '浙江省杭州市江干区笕桥镇天城路58号闸弄口派出所', '123456', '火影忍者手办', '120.204988', '30.288278', '菜鸟', '手办', '1');
INSERT INTO `express` VALUES ('2', '3', '浙江省杭州市江干区彭埠镇天城路1号五芳斋', '123232', '清风餐巾纸', '120.211499', '30.290941', '天天', '餐巾纸', '2');
INSERT INTO `express` VALUES ('3', '2', '浙江省杭州市江干区四季青街道杭海路438号渔人码头', '232321', '迪迦奥特曼', '120.221465', '30.261189', '顺丰', '奥特曼', '3');
INSERT INTO `express` VALUES ('4', '1', '浙江省杭州市江干区闸弄口街道机场路一巷163-14号新疆棉胎', '123222', '苹果手机X', '120.189853', '30.291407', '顺丰', 'iphone', '4');
INSERT INTO `express` VALUES ('5', '1', '浙江省杭州市江干区闸弄口街道艮山西路200号布丁酒店', '323211', '这是我的新电脑', '120.197112', '30.276682', '顺丰', 'ThinkPad', '5');
INSERT INTO `express` VALUES ('6', '1', '浙江省杭州市拱墅区祥符街道莫干山路766号杭州汽车北站', '589659', '好吃的面包。', '120.113282', '30.317138', '圆通', '面包', '6');
INSERT INTO `express` VALUES ('7', '2', '浙江省嘉兴市海宁市盐官镇人民路38号盐官百里钱塘观潮景区', '223232', '不是所有牛奶都叫特仑苏！', '120.544127', '30.403695', '顺丰', '特仑苏牛奶', '7');
INSERT INTO `express` VALUES ('8', '1', '浙江省嘉兴市海宁市盐官镇人民路38号盐官百里钱塘观潮景区', '232321', '这是一个保温杯~', '120.544127', '30.403695', '天天', '魔膳师保温杯', '8');
INSERT INTO `express` VALUES ('9', '2', '浙江省杭州市江干区笕桥镇天城路97号万家花园(西北门)', '859865', '新的键盘~', '120.203918', '30.287202', '申通', '键盘', '9');
INSERT INTO `express` VALUES ('10', '2', '浙江省杭州市江干区笕桥镇天城路97号万家花园(西北门)', '123456', '北冰洋', '120.213843', '30.262661', '申通', '好喝的北冰洋', '10');

-- ----------------------------
-- Table structure for extra
-- ----------------------------
DROP TABLE IF EXISTS `extra`;
CREATE TABLE `extra` (
  `extraId` int(11) NOT NULL AUTO_INCREMENT,
  `fromNum` varchar(255) DEFAULT NULL,
  `expressId` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `idcardnum` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`extraId`),
  KEY `FK_o0ocmcsqa12byo5f6b7423ilh` (`expressId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of extra
-- ----------------------------

-- ----------------------------
-- Table structure for idcard
-- ----------------------------
DROP TABLE IF EXISTS `idcard`;
CREATE TABLE `idcard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idcardnum` varchar(18) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of idcard
-- ----------------------------
INSERT INTO `idcard` VALUES ('1', '330108199702010910', '傅郑锋');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `fromNum` varchar(255) DEFAULT NULL,
  `msgType` int(11) DEFAULT NULL,
  `orderDate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `passivePer` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  KEY `FK_7tqcvba48dkw4t0nw2ygb6a6r` (`passivePer`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', 'PH20180829172217003', '1', '2018-09-27 14:45:19', '2', '1', '2');

-- ----------------------------
-- Table structure for publish
-- ----------------------------
DROP TABLE IF EXISTS `publish`;
CREATE TABLE `publish` (
  `publishId` int(11) NOT NULL AUTO_INCREMENT,
  `fromNum` varchar(255) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `publish_date` datetime DEFAULT NULL,
  `requirement` double DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `take_date` datetime DEFAULT NULL,
  `publisher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`publishId`),
  KEY `FK_pv27bvc28ptguqty9e2nx1wwy` (`publisher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publish
-- ----------------------------
INSERT INTO `publish` VALUES ('1', 'PH20180829171751000', '5', '2018-08-29 17:17:51', '5', '2', '2019-02-02 01:01:00', '1');
INSERT INTO `publish` VALUES ('2', 'PH20180829171845001', '4', '2018-08-29 17:18:45', '4', '2', '2019-01-01 00:00:00', '1');
INSERT INTO `publish` VALUES ('3', 'PH20180829171919002', '5', '2018-08-29 17:19:19', '4', '2', '2020-03-03 01:01:00', '1');
INSERT INTO `publish` VALUES ('4', 'PH20180829172217003', '5', '2018-08-29 17:22:17', '4', '4', '2019-02-02 01:01:00', '2');
INSERT INTO `publish` VALUES ('5', 'PH20180829172321004', '4', '2018-08-29 17:23:21', '4', '2', '2019-02-02 01:01:00', '2');
INSERT INTO `publish` VALUES ('6', 'PH20180906093751000', '0', '2018-09-06 09:37:51', '5', '1', '2018-11-06 01:37:00', '1');
INSERT INTO `publish` VALUES ('7', 'PH20180906144457000', '0', '2018-09-06 14:44:57', '4', '1', '2019-12-01 00:00:00', '1');
INSERT INTO `publish` VALUES ('8', 'PH20180906144625001', '0', '2018-09-06 14:46:25', '4', '1', '2018-12-31 23:03:00', '1');
INSERT INTO `publish` VALUES ('9', 'PH20180906145854003', '0', '2018-09-06 14:58:54', '5', '1', '2019-01-01 00:00:00', '1');
INSERT INTO `publish` VALUES ('10', 'PH20180906145628002', '0', '2018-09-06 14:56:28', '4', '1', '2019-01-01 00:00:00', '1');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `record` varchar(255) DEFAULT NULL,
  `record_date` datetime DEFAULT NULL,
  `record_state` int(11) DEFAULT NULL,
  `sended_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`recordId`),
  KEY `FK_ac4fviydos6721n7yohumt0nb` (`sended_id`),
  KEY `FK_jfe2xd5yxfr50iagoh6mx3ltv` (`sender_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `requestId` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `free_date` varchar(255) DEFAULT NULL,
  `fromNum` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `request_date` datetime DEFAULT NULL,
  `requester_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`requestId`),
  KEY `FK_5u7yu0v9sd1l013jhc9yx2me0` (`requester_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES ('1', '浙江省杭州市江干区笕桥镇天城路72号WENSLI', '2019-01-01T00:00', 'ES20180906141402000', '今天很空闲。', '2018-09-06 14:14:02', '1');
INSERT INTO `request` VALUES ('2', '浙江省杭州市江干区笕桥镇天城路58号闸弄口派出所', '2019-02-02T00:00', 'ES20180906141522001', '可以帮忙拿快递~', '2018-09-06 14:15:22', '1');
INSERT INTO `request` VALUES ('3', '浙江省杭州市江干区笕桥镇天城路74号万事利大厦(南门)', '2019-09-29T01:01', 'ES20180906142225000', '有没有人需要拿快递的~', '2018-09-06 14:22:25', '1');
INSERT INTO `request` VALUES ('4', '浙江省杭州市西湖区西湖街道孤山路25-1号湖心亭', '2020-02-01T01:00', 'ES20180906142612000', '大学四年帮忙拿快递~', '2018-09-06 14:26:12', '1');
INSERT INTO `request` VALUES ('5', '浙江省嘉兴市海宁市盐官镇人民路38号盐官百里钱塘观潮景区', '2019-02-02T01:01', 'ES20180906144319000', '我在钱塘江里帮你拿快递', '2018-09-06 14:43:19', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `credit_level` double DEFAULT NULL,
  `image` varchar(255) DEFAULT '0',
  `integral` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `tidcard` int(11) DEFAULT '-1',
  PRIMARY KEY (`userId`),
  KEY `FK_ip51mov1yqa6yl6m5adavu8u9` (`tidcard`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '杭州', '4.8', '../avatar/defaultavatar.jpg', '52', 'fuzf', '111111', '1', '1', '17826863260', '0', '1');
INSERT INTO `user` VALUES ('2', '湖南', '5', '../avatar/defaultavatar.jpg', '55', 'lisp', '111111', '1', '1', '18758286501', '0', '1');
INSERT INTO `user` VALUES ('3', '西安', '5', '../avatar/defaultavatar.jpg', '50', 'mady', '111111', '1', '0', '15355019667', '0', '1');
INSERT INTO `user` VALUES ('4', '杭州', '2', '../avatar/defaultavatar.jpg', '50', 'fuzx', '111111', '2', '0', '15868832106', '0', '-1');

