/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : phone

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 26/03/2019 10:45:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accesstoken
-- ----------------------------
DROP TABLE IF EXISTS `accesstoken`;
CREATE TABLE `accesstoken`  (
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'token',
  `expires_in` int(5) NULL DEFAULT NULL COMMENT '有效时间 单位: S',
  `invalidTime` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '失效时间 =  now（）+expires_in',
  `type` int(1) NULL DEFAULT 0 COMMENT '分为普通token（0），和通讯录token（1）',
  PRIMARY KEY (`invalidTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for birthday
-- ----------------------------
DROP TABLE IF EXISTS `birthday`;
CREATE TABLE `birthday`  (
  `bid` int(11) NOT NULL AUTO_INCREMENT COMMENT '生日祝福ID',
  `content` varchar(666) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文本\r\n',
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of birthday
-- ----------------------------
INSERT INTO `birthday` VALUES (1, '愿如梁上燕，岁岁常相见。生日快乐！');
INSERT INTO `birthday` VALUES (2, '在你永远与春天接壤的梦幻里。祝你：心想事成幸福快乐!生日快乐!');
INSERT INTO `birthday` VALUES (3, '愿这完全属于你的一天带给你快乐，愿未来的日子锦上添花!');
INSERT INTO `birthday` VALUES (4, '愿以后有人陪你烧烤火锅焖大虾，也有人陪你素琴调弦品香茶。');
INSERT INTO `birthday` VALUES (5, '愿你有一个灿烂的前程，愿你有情人终成眷属，愿你在尘世获得幸福。');
INSERT INTO `birthday` VALUES (6, '愿时光能缓，愿故人不散；愿你惦念的人能和你道晚安，愿你独闯的日子里不觉得孤单。');
INSERT INTO `birthday` VALUES (7, '愿你如阳光，明媚不忧伤，愿你拥有雌雄同体的灵魂，活得嚣张 永不受伤。');
INSERT INTO `birthday` VALUES (8, '愿你三冬暖，愿你春不寒。愿你天黑有灯，下雨有伞。愿你路上有良人伴 。');
INSERT INTO `birthday` VALUES (9, '愿你回望过去皆是无悔，展望前程已然不辜。');
INSERT INTO `birthday` VALUES (10, '愿你三冬暖，愿你春不寒，愿你天黑有灯，下雨有伞，愿你一路上，有良人相伴。');
INSERT INTO `birthday` VALUES (11, '转眼间一年又过去，今天又是你的生日了，愿今天你拥有世上一切美丽的东西，来年生日更美好，更快乐！一年胜一年青春！生日快乐！');
INSERT INTO `birthday` VALUES (12, '愿你无忧无疾百岁安生不离笑，盼你春暖花开一生喜乐幸福绕。');
INSERT INTO `birthday` VALUES (13, '愿你去往之地皆为热土，愿你所遇之人皆为挚友。');
INSERT INTO `birthday` VALUES (14, '花朝月夕，如诗如画；在这个真正属于你的日子里，愿你拥抱未来，愿你的容颜像春天般绚烂。');
INSERT INTO `birthday` VALUES (15, '祈望你心灵深处芳草永绿，青春常驻，笑口常开。祝你生日快乐，健康幸福!');
INSERT INTO `birthday` VALUES (16, '愿你有自持而节制的华丽，层出不穷的新鲜愈炼愈飞的魔性，永远虔诚的天真。');
INSERT INTO `birthday` VALUES (17, '执君手，慢同行，不问前路风疏马聚。');
INSERT INTO `birthday` VALUES (18, '愿你一生努力，一生被爱，想要的都拥有，得不到的都释怀，只愿你被这世界温柔相待。');
INSERT INTO `birthday` VALUES (19, '愿你一生温暖纯良，不舍爱与自由。');
INSERT INTO `birthday` VALUES (20, '再没骗自己的理由，时间如刀不再温柔，生日愿望一夜暴富。生日快乐！');
INSERT INTO `birthday` VALUES (21, '莫失莫忘，仙寿恒昌；不离不弃，芳龄永继。生日快乐！');

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `invID` int(11) NOT NULL AUTO_INCREMENT COMMENT '发票ID',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '发票二维码内容',
  `pubDate` datetime(0) NULL DEFAULT NULL,
  `userId` int(11) NULL DEFAULT NULL COMMENT '外键',
  `reimbursement` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销人',
  `billsId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报销单号',
  PRIMARY KEY (`invID`, `content`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES (1, '123', '2019-03-06 17:03:33', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (3, '﻿你好呀', '2019-03-06 19:23:25', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (4, 'https://open.weixin.qq.com/sns/getexpappinfo?appid=wxce0c4f38650cfe54&path=pages%2Findex%2Findex.html#wechat-redirect', '2019-03-06 19:37:59', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (5, '﻿测试发票1', '2019-03-07 11:32:15', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (6, '88011929', '2019-03-07 11:32:45', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (7, '6901028193498', '2019-03-07 11:38:25', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (9, '01,01,4403183130,05370195,5009.43,20190301,,2301,', '2019-03-07 11:47:39', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (10, '01,01,4400184130,18532887,14208.32,20190219,,AD5D,', '2019-03-07 11:47:58', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (12, '01,10,044001609111,37143540,172.41,20190129,84798234631222754768,A514,', '2019-03-07 11:48:14', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (13, '01,01,4400191130,10040609,6294.81,20190304,,62E7,', '2019-03-07 11:48:35', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (16, '01,04,037021800104,08751997,17825.24,20181218,72058802573037714736,EF65,', '2019-03-07 12:58:45', 1, NULL, NULL);
INSERT INTO `invoice` VALUES (17, 'aaaaaaaaaaaaaaaa', '2019-03-08 20:06:14', 2, NULL, NULL);
INSERT INTO `invoice` VALUES (20, '﻿特定用户判断', '2019-03-11 14:16:26', 3, NULL, NULL);
INSERT INTO `invoice` VALUES (21, '﻿二维码11111111111111111111111111111111111111111', '2019-03-11 14:35:31', 3, NULL, NULL);
INSERT INTO `invoice` VALUES (22, '﻿二维码222222222222222222222222222222222222222222222222222222222222222222222222222222222', '2019-03-11 14:35:34', 3, NULL, NULL);
INSERT INTO `invoice` VALUES (24, '﻿二维码3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', '2019-03-11 15:51:09', 3, '熊建林', 'aa123456');
INSERT INTO `invoice` VALUES (27, '二维码44444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444', '2019-03-11 17:18:16', 1, '66', '777777777');

-- ----------------------------
-- Table structure for members
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members`  (
  `userid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成员UserID。对应管理端的帐号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成员名称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可',
  `enable` int(1) NULL DEFAULT NULL COMMENT '成员启用状态。1表示启用的成员，0表示被禁用。',
  `birthday` date NULL DEFAULT NULL COMMENT '月-日',
  `entryTime` date NULL DEFAULT NULL COMMENT '年-月-日',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of members
-- ----------------------------
INSERT INTO `members` VALUES ('allen', '张能', 'https://p.qlogo.cn/bizmail/mGCn25mZ1CDGS4wgR9qXkcEzZ5IEN6ZGCV4aw6ZloMoFHKicZxaHrrw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ane', '肖伟', 'http://p.qlogo.cn/bizmail/23RCvhLXtYBo51dOwt5EnhdP6maL0m2gVvTIfmnuYvf7SfLmE8PNVA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('aoXiYiAoXiao', 'Ao西一凹肖', 'http://p.qlogo.cn/bizmail/frIibDpfhHicHwGQCdyPM9ib2NiaIiaU1c5BCE0jpIeBcs42d6LCX1iaU5mg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('asilver', 'Asilver', 'http://p.qlogo.cn/bizmail/QaUF65GJjKJI4mqMHMDA6UjdELRM6BEQRGgazibflr3W1YbKqibaibibMQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('atina', '李春艳', 'http://p.qlogo.cn/bizmail/cjfsjbBEU9toibEk7gdYvIOg9bZ0b2SgpiciaicEgTLFiaLFeGxmQfNa5ng/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('battle', '邵波', 'http://p.qlogo.cn/bizmail/KGbztKJKAOA8o3khibK7xicibfpKVeSXhZJ7JYqYxFuG1ibxpBhiaZ52PUQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('BinZiYa', '毛缤梓', 'https://p.qlogo.cn/bizmail/4v4uQ9TyNj4ICmTQmPwBZVADhQmvYc24CBygUVd7Y9xMbAAu6QL2dg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ChenFeng', '晨风', 'http://p.qlogo.cn/bizmail/l5b2Wlh7ZiatYibwYUsBYTukIf4VYUcaCSVRNbNznfy5NL2rnqRzEApQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ChenJiaJian', '陈家健', 'http://p.qlogo.cn/bizmail/gYutIdLALyS6OuGhiaMJo7rwkEm0tE1qdJQafpQcxGksEl6EFyiagAqQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ChenJiaYongShao', '陈智勇', 'http://p.qlogo.cn/bizmail/vPu5kuhqgqAvL48yllok7o4uo9zCNM9xTAhHNlZ392V8wMUCrbRxDQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('DaKeYun', '大可樂、', 'http://p.qlogo.cn/bizmail/p0FJnj8hUpeW8wBomlYqiarjWB0tcPdHibHKiafU696FcZSKI0RJtRl1Q/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('DengQiangKe', '邓强科', 'http://p.qlogo.cn/bizmail/OKAF8DwuWIcGYqAu72sdrwRdUBdfUibkM7LK8AyXA4ibibs2P9u7fdJAA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('DengYaABin', '邓亚啊斌', '', 1, NULL, NULL);
INSERT INTO `members` VALUES ('edwardtang', 'Edward Tang', 'http://p.qlogo.cn/bizmail/4vByzkob5LdhMHwdFYgxxuEghrVALicialjTxWKnyZex7NWctHnRZroA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('FangJingLang', '方京浪', 'https://p.qlogo.cn/bizmail/LpNOiah1rjxFpMJFC4wXImUibWgOCXog0yJMH57HxUzEkI6T1KWSJWQw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Feng', '张绍峰', 'http://p.qlogo.cn/bizmail/nWGiaW9Oz7mI98IWX5cp6zfjiczlGsOaqiazeNNH6FNIiboiaskW6fIbEUA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('finallysmile.', 'Finally Smile.', 'http://p.qlogo.cn/bizmail/w1egshbth7cwyH6uc9gamfzjvNNY1nznjUmNb9B4OLCu9v5W2mwysw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('GaoMeng', '高猛', 'http://p.qlogo.cn/bizmail/xrtlJf4C0uu4JhWK9gI753JOK5EVFUjaniavQ7tViceBHvzfPBcGPEibA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('GongYongFeng', '龚永锋', 'http://p.qlogo.cn/bizmail/PhyLyQGCy6AcbAAFAn8tCxzuLpowic9AkcEUugp3deZwH2VcHOEDoAQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('guardianyou', '张通', 'https://p.qlogo.cn/bizmail/cI32oRCjOYUuo1ia9ysxQbHQiamoKDyjhAicAuoY3C5dSIF2cmzv3VBqA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('HeXiang', '何祥', '', 1, NULL, NULL);
INSERT INTO `members` VALUES ('himan', 'Hi man', 'http://p.qlogo.cn/bizmail/rKFpibHJfCxdT96kg4XctmWCbBcg7HPOhtkoyrUVpoZqLhtX0nuWnfA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Hong', '蒋鸿洲', 'http://p.qlogo.cn/bizmail/jP1ia1qibexOQYxkPR0U7ibCAcorHWb4Sp9lm1Qh8AZJgGo1Lo0ra3h6Q/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('HuoChaiYu', '火柴鱼', 'https://p.qlogo.cn/bizmail/icUzDOlWf8p2w0UFXQESnwByibq1C7xWemoRmTib0iaCwmrg0AXm53zicCA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('jackliang', '梁星河', 'http://p.qlogo.cn/bizmail/MyXBZGfxOaRESSSH1Alxr88jWkZZAiaXaBMrQNtuDib7RF3ryYJRia2eg/0', 1, '1970-03-19', '1900-01-10');
INSERT INTO `members` VALUES ('jankinli', '李江涛', 'https://p.qlogo.cn/bizmail/uVyZWE2zdL4gM0uq3cQ0SuicQAJTfY3pTALBudNtfmvKfrHMcKu4zOw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Ji', '纪', 'http://p.qlogo.cn/bizmail/VTFhxocYvVsMUJNZna3PwewT6GfkLBjgefCVSmAnAiavdlnUVA05D6Q/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('JinTaiLang', '金太郎', 'http://p.qlogo.cn/bizmail/IIO5rBfxS6ZI4OtAdaHbRbRnNrXQicDNuy5fRndQ6qq4HHjH1R7AWYw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('JinYouDiYiLai', '颜陈玲', 'http://p.qlogo.cn/bizmail/iaN6tafZYpzR7Q2qia52HPFHRn1URDTOPcm9rEEHE0Tv5Jb37EdicdhSQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('LiangZhiQian', '梁志谦', 'http://p.qlogo.cn/bizmail/e2hBgeVY0mdfw5eEx9qS1stLUsAzmMJlmmSvNicjbSrqeicTib3m0wkTQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('LiXiaoHui', '李孝辉', 'https://p.qlogo.cn/bizmail/n3ibmbbYU6gk7zTEw6f56ySq50wQ7MV6NibXeyv9HNSlGKwZuMQxWAFA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('LongShiLong', '龙世隆', 'http://p.qlogo.cn/bizmail/T6GD5Rg4Xh9ciatxh3AcnYJh0QJ5m4ujVzMhZRWt06cvKuPqCxr5w0A/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('lucky', 'lucky', 'http://p.qlogo.cn/bizmail/cxSKjwz3ymPHJvlwZgic6IKsaQ7lT3190qwiaj97o3eqh9E3vYFxvlgw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('MaBin', '马斌', 'https://p.qlogo.cn/bizmail/kkxDUpCtibskCXYJY16jPfD88W8iclQoozXpMLsq7mMYib2ibghGZcTXWw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('MoHaiBo', '莫海波', 'http://p.qlogo.cn/bizmail/pflwlDn9zzfaRY1OXZ5hnNrggaZLZugLVbatIYUIQTpy8wqAG3E9bQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('MoQiMin', '莫启敏', 'https://p.qlogo.cn/bizmail/bVSAVm2r01SEic0L7h0tj1ZVUP1o3yGhtzyErBHaORpvz0cOImgiaCVQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('nemo', '王维', 'https://p.qlogo.cn/bizmail/DjkclMKSn2OZHcrR899LzMibfxWxXj8fpuH13Ooh9aFc6oK6HV9SoibQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Qiu', '仇江胜', 'http://p.qlogo.cn/bizmail/blsMWLckmT0j0N9cTbkkt8B6Y28XLGu7kzbf4M9bxRrkeZovYSNIOQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('RanNuo', '然诺', 'https://p.qlogo.cn/bizmail/Y866k6t7EEQBk1dVoWtP1IJU2L884dr1r9o1Pq0rFRtKibcUHrVaMHQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ShaoRui', '邵瑞', 'http://p.qlogo.cn/bizmail/I3lib9tHMywvQmz0k5JyyULHujXBAbCOMuK7uZOvVwyqHwdelibJgWMQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ShenJinTang', '洪镇锦', 'http://p.qlogo.cn/bizmail/Gfp72fkK9dvVhohqqLVPYDWkeRPhMsoFpibu5ibu4k2eEqq3o6UicImpg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ShiHeYuanFang', '诗和远方', 'http://p.qlogo.cn/bizmail/WWGIWgHiavTSwic7Qbx56zzEfcA9iaNXqnSd4YDFTn9BhWdmQmDiby9r6g/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ShuiHuoXiangJi', '水火相济', 'https://p.qlogo.cn/bizmail/VveTYy328tMuhLiaOnv3WWHicyJtxJN0xMRph0aCvzZggibiaG4z0sCWew/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('thrive', '梁光照', 'https://p.qlogo.cn/bizmail/P8360lrgA607vlic5QLExVF9TDEcibGc4vq2oeJzYlxHt5iap4pTkia9fw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('TianJie', '田杰', 'http://p.qlogo.cn/bizmail/OgSqVO1VlW3A14Nj8tvju0ia0Ap8n4oY3m8xNwQWOYHPW2RzCV3ic8cg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('tom', '秦智', 'http://p.qlogo.cn/bizmail/9zd82gkzxqoBxz3CIEU0kKTxO7pMmZ5Viaibt1Pa68AicEhWlrruZW42A/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('TuGuoHuan', '涂国焕', 'https://p.qlogo.cn/bizmail/U38RUUAG8F4abfmJNTLLZRz4G335dgHT8ibO6kG3j1OwLPT3RptWibMg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('WeiJianPeng', '卫建鹏', 'http://p.qlogo.cn/bizmail/vPzFVwjOGIicZXHAv3djpu8ibQnVhqWG0Bib9BCFmN7ibSS2YibM0YM4lzg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('wendy', 'wendy', 'http://p.qlogo.cn/bizmail/c9XQmjDj2bh6UcicvYfTkT3qeTsoURuWc7b6193XdDXhCl3s4hxVwWg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('WuJianJun', '吴剑君', 'http://p.qlogo.cn/bizmail/5U3ibwhibEtJq4Fd3J0De2DticWaqoCmB3dW0MrEdF6Sw6GFb5zzBfV4A/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('WuShangMin', '吴尚敏', 'http://p.qlogo.cn/bizmail/CKDJE3XstraDhV64Xjpic6a9GKhicFw091b8CbJ8icosZjgQ5dbYgTfaQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('XieXiaoQing', '谢晓青', 'http://p.qlogo.cn/bizmail/7lM8SB7PCTstkv8JFa5WNk3tcevPPRIwdAFBpq33sXDE6l945qWWcA/0', 1, NULL, '2019-01-01');
INSERT INTO `members` VALUES ('XiongJianLin', '熊健淋', 'http://p.qlogo.cn/bizmail/jEPmFUcHfXewUYUDYbNjD1CIz7ibricKKZrlJqiaGNwmfAKSSrofjXosQ/0', 1, NULL, '2019-03-12');
INSERT INTO `members` VALUES ('xius', 'xius', 'http://p.qlogo.cn/bizmail/dv5ygDssGONH0uCAttWZibJjniaqYjcyCJIIn6rE3jwux1Ks24n558Lw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('XuGuangRong', '徐光容', 'http://p.qlogo.cn/bizmail/74v55nuaLXjiaCg5CFZ3F0PhU5SmG6jSnd8K9Jkze7QTZweasFwBGTQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Yan', '颜晓疆', 'http://p.qlogo.cn/bizmail/blcuZnIOHHian1AvJsiaTzaGiaK21XHDfsyYp550JqSZKmXQicZ0r6ylqA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YangFanQiHang', '顾建波', 'http://p.qlogo.cn/bizmail/evJ4IPkYVLTdV5sauKAoL5xiacwPqpGct2bhbLnkuwhRz3ibQXy4zstQ/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YangGuang', '阳光', 'http://p.qlogo.cn/bizmail/OeL3bctibcBVJ5ckYLdC93n1iaP7qLmpt9vwu5HpNcExOBkgURpJtg8w/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YangHaiOu', '杨海欧', 'http://p.qlogo.cn/bizmail/KI543A9ZDP7iaprZ1PvD9zwYF7aDC0P5UmUW6BVjGdx9FharCibObQ0Q/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YangJunJun', '杨钧钧', 'http://p.qlogo.cn/bizmail/13ywB9sUVYB0rDGVvwDYhFuQwsQh3JQgUWZGvNibp6gOwC96rmcbFYA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YangXiaoDong', '杨小东', 'https://p.qlogo.cn/bizmail/1Ibj1gpoWSocJu9w2WdaUKsrAtuvVicoE9JYNCch3CauGJn3bLVmH8A/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YeBingZhao', '叶炳昭', 'https://p.qlogo.cn/bizmail/E3mfVwl4NzQNXGzNiaUWibibyzziceTmg2x0kocHBs7SVG43UuMEJyTaWA/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('YeZhiMan', '叶志满', 'http://p.qlogo.cn/bizmail/sRd3z0kNU3KRg0bwKQzMTgZkSdfZwsib5uzSspMhcZoUNP0oNpW5mHw/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('Yu', '于', 'http://p.qlogo.cn/bizmail/IDTMkWpfpXSP1DDcCahsMiaZQt0PxRiadSEItkn6ssIl5oCpqHJrozKg/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ZhangZhongHan', '张忠汉', 'http://p.qlogo.cn/bizmail/JCJpEDMtxnX97UTFWrcT5MtK6PkptP5ZWVHQu57ImMPM6psRYXW72g/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ZhaoShuang', '赵双', 'https://p.qlogo.cn/bizmail/Sia9cjlvmafkiaUPVkdl7hSpKdSgGMCcBeRNqZGM39fMu3MiaVya32q1Q/0', 1, NULL, NULL);
INSERT INTO `members` VALUES ('ZhongXiang', '钟翔', 'https://p.qlogo.cn/bizmail/qGKcwf3g3hJd7qd6WM2jEdhfvziaA858NRj2tLhHuTVuoyT5IVa2PlQ/0', 1, NULL, NULL);

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session`  (
  `sessionKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一指定用户标识符',
  `openId` varchar(99) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '临时会话秘钥',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`sessionKey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `openId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户唯一标识符',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'aaaa', 'oxxHM4s53H345645646464564');
INSERT INTO `user` VALUES (2, 'bbbbb', '3333333333');
INSERT INTO `user` VALUES (3, 'XiongJJJJ', 'asdfasasdfasdf');
INSERT INTO `user` VALUES (4, 'xiongsdadad', 'oxxHM4s53Hmcnasdfsdf');
INSERT INTO `user` VALUES (5, 'xiongsadfasdf', 'asdfsd');
INSERT INTO `user` VALUES (6, 'xiong', 'oxxHM4s53HmcnyOdy6bgolJakvnQ');

-- ----------------------------
-- Procedure structure for autoDel
-- ----------------------------
DROP PROCEDURE IF EXISTS `autoDel`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `autoDel`()
BEGIN
delete from session   where datediff(now(),createTime) > 1;
end
;;
delimiter ;

-- ----------------------------
-- Event structure for autoDelToken_event
-- ----------------------------
DROP EVENT IF EXISTS `autoDelToken_event`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` EVENT `autoDelToken_event`
ON SCHEDULE
EVERY '240' MINUTE STARTS '2019-03-12 14:30:08'
ON COMPLETION PRESERVE
DO delete from 	accesstoken   WHERE invalidTime < now()
;;
delimiter ;

-- ----------------------------
-- Event structure for autoDel_event
-- ----------------------------
DROP EVENT IF EXISTS `autoDel_event`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` EVENT `autoDel_event`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2019-03-11 16:40:19'
ON COMPLETION PRESERVE
DO delete from session   where datediff(now(),createTime) > 1
;;
delimiter ;

-- ----------------------------
-- Event structure for eventJob
-- ----------------------------
DROP EVENT IF EXISTS `eventJob`;
delimiter ;;
CREATE DEFINER = `root`@`localhost` EVENT `eventJob`
ON SCHEDULE
EVERY '86400' SECOND STARTS '2019-03-08 15:42:30'
ON COMPLETION PRESERVE
DO call autoDel()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
