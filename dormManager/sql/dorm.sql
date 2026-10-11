-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: my_dorm
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adjust_room`
--
create database my_dorm;
use my_dorm;
DROP TABLE IF EXISTS `adjust_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adjust_room` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '申请人',
  `current_room_id` bigint NOT NULL COMMENT '当前房间',
  `towards_room_id` bigint NOT NULL COMMENT '目标房间',
  `state` enum('未处理','通过','驳回') NOT NULL DEFAULT '未处理' COMMENT '申请状态',
  `apply_time` varchar(255) NOT NULL COMMENT '申请时间',
  `finish_time` varchar(255) DEFAULT NULL COMMENT '处理时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='申请换宿表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adjust_room`
--

LOCK TABLES `adjust_room` WRITE;
/*!40000 ALTER TABLE `adjust_room` DISABLE KEYS */;
INSERT INTO `adjust_room` VALUES (1890049245831979009,3,1889688340178903042,1889901340466831361,'通过','Thu Feb 13 22:43:50 CST 2025',NULL,NULL,NULL,'2025-02-13 14:43:50',NULL,NULL),(1890399746317627393,4,1889688340178903042,1889713593437372418,'通过','Fri Feb 14 21:56:36 CST 2025',NULL,'1换到2',NULL,'2025-02-14 13:56:36',NULL,NULL);
/*!40000 ALTER TABLE `adjust_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dorm_build`
--

DROP TABLE IF EXISTS `dorm_build`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dorm_build` (
  `id` bigint NOT NULL COMMENT '主键',
  `build_id` bigint NOT NULL COMMENT '宿舍楼号码',
  `build_name` varchar(255) NOT NULL COMMENT '宿舍楼名称',
  `build_detail` varchar(255) NOT NULL COMMENT '宿舍楼备注',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍楼表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dorm_build`
--

LOCK TABLES `dorm_build` WRITE;
/*!40000 ALTER TABLE `dorm_build` DISABLE KEYS */;
INSERT INTO `dorm_build` VALUES (1,1,'一号楼','男宿舍',NULL,NULL,'2025-02-12 12:10:05',NULL,'2025-02-12 12:10:05'),(2,2,'二号楼','女宿舍',NULL,NULL,'2025-02-12 12:10:05',NULL,'2025-02-12 12:10:05'),(3,3,'三号楼','男宿舍',NULL,NULL,'2025-02-12 12:10:05',NULL,'2025-02-12 12:10:05'),(4,4,'四号楼','女宿舍',NULL,NULL,'2025-02-12 12:10:05',NULL,'2025-02-12 12:10:05'),(1889648395397619714,99,'99','99','2222',NULL,'2025-02-12 12:11:00',NULL,'2025-02-12 12:14:18'),(1889649267846381570,11,'99999','99','99',NULL,'2025-02-12 12:14:28',NULL,NULL);
/*!40000 ALTER TABLE `dorm_build` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dorm_check`
--

DROP TABLE IF EXISTS `dorm_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dorm_check` (
  `check_id` bigint NOT NULL COMMENT '检查记录ID，自增',
  `dorm_id` bigint NOT NULL COMMENT '宿舍ID，外键，指向宿舍表',
  `check_date` date NOT NULL COMMENT '检查日期',
  `checker_name` varchar(100) NOT NULL COMMENT '检查人姓名',
  `cleanliness_score` int NOT NULL COMMENT '卫生评分，0-100分',
  `check_status` enum('合格','不合格') DEFAULT '合格' COMMENT '检查状态，合格或不合格',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍卫生检查表，记录每次宿舍的卫生检查情况';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dorm_check`
--

LOCK TABLES `dorm_check` WRITE;
/*!40000 ALTER TABLE `dorm_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `dorm_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dorm_room`
--

DROP TABLE IF EXISTS `dorm_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dorm_room` (
  `id` bigint NOT NULL COMMENT '主键Id',
  `room_no` varchar(255) NOT NULL COMMENT '宿舍房间号',
  `build_id` bigint NOT NULL COMMENT '宿舍楼号',
  `floor_num` int NOT NULL COMMENT '楼层',
  `max_capacity` int NOT NULL DEFAULT '4' COMMENT '房间最大入住人数',
  `current_capacity` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '当前房间入住人数',
  `electricity` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '剩余电/ ？度',
  `water` int NOT NULL DEFAULT '0' COMMENT '剩余？吨水',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dorm_room`
--

LOCK TABLES `dorm_room` WRITE;
/*!40000 ALTER TABLE `dorm_room` DISABLE KEYS */;
INSERT INTO `dorm_room` VALUES (1889688340178903042,'1',1889649267846381570,1,1,1,12500,0,'1',NULL,'2025-02-12 14:49:44',NULL,NULL),(1889713593437372418,'2',1889649267846381570,2,2,0,125,0,'2',NULL,'2025-02-12 16:30:05',NULL,NULL),(1889901340466831361,'Y1518',1,1,3,1,125,0,NULL,NULL,'2025-02-13 04:56:07',NULL,NULL);
/*!40000 ALTER TABLE `dorm_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dorm_room_user`
--

DROP TABLE IF EXISTS `dorm_room_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dorm_room_user` (
  `id` bigint NOT NULL COMMENT '主键Id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `room_id` bigint NOT NULL COMMENT '宿舍房间号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍人员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dorm_room_user`
--

LOCK TABLES `dorm_room_user` WRITE;
/*!40000 ALTER TABLE `dorm_room_user` DISABLE KEYS */;
INSERT INTO `dorm_room_user` VALUES (1890402298200588290,1,1889901340466831361,NULL,NULL,'2025-02-14 14:06:45',NULL,NULL);
/*!40000 ALTER TABLE `dorm_room_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` bigint NOT NULL COMMENT '订单id',
  `user_id` bigint NOT NULL COMMENT '充值人',
  `dorm_id` bigint NOT NULL COMMENT '宿舍Id',
  `order_type` enum('水费','电费','其他') NOT NULL COMMENT '订单类型',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `status` enum('未完成','已完成') DEFAULT '未完成' COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1889671221483569154,1,1,'水费',100.00,'未完成',NULL,NULL,'2025-02-12 13:41:42',NULL,NULL),(1889671421002416129,1,1,'水费',200.00,'未完成',NULL,NULL,'2025-02-12 13:42:30',NULL,NULL),(1889671725995425793,1,1,'电费',200.00,'未完成',NULL,NULL,'2025-02-12 13:43:43',NULL,NULL),(1889671985434087426,1,1,'电费',200.00,'未完成',NULL,NULL,'2025-02-12 13:44:44',NULL,NULL),(1889672149821444098,1,1,'电费',200.00,'未完成',NULL,NULL,'2025-02-12 13:45:24',NULL,NULL),(1890012897989328898,1,1889688340178903042,'电费',10000.00,'已完成','1',NULL,'2025-02-13 12:19:24',NULL,NULL),(1890023301159272450,1,1889901340466831361,'电费',100.00,'已完成',NULL,NULL,'2025-02-13 13:00:45',NULL,NULL),(1890400101340295170,4,1889713593437372418,'电费',100.00,'已完成',NULL,NULL,'2025-02-14 13:58:01',NULL,NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair`
--

DROP TABLE IF EXISTS `repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repair` (
  `id` bigint NOT NULL COMMENT '订单编号',
  `repairer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '报修人',
  `build_id` bigint NOT NULL COMMENT '报修宿舍楼',
  `room_id` bigint NOT NULL COMMENT '报修宿舍房间号',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '表单标题',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '表单内容',
  `state` enum('完成','未完成') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '未完成' COMMENT '订单状态（是否维修完成）',
  `order_build_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `order_finish_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='保修表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair`
--

LOCK TABLES `repair` WRITE;
/*!40000 ALTER TABLE `repair` DISABLE KEYS */;
INSERT INTO `repair` VALUES (1890016398668677121,'admin',1,1889901340466831361,'12323','<p>21321</p>','未完成','2025-02-13 08:00:00',NULL),(1890016498988040193,'admin',1889649267846381570,1889688340178903042,'213','<p>12312</p>','完成','2025-02-19 08:00:00','2025-02-25 08:00:00'),(1890399249049333762,'admin',1889649267846381570,1889688340178903042,'12','<p>1</p>','完成','2025-02-01 08:00:00','2025-02-14 08:00:00'),(1890399396328124417,'王五同学',1889649267846381570,1889688340178903042,'1','<p>1</p>','未完成','2025-02-26 08:00:00','2025-02-26 08:00:00');
/*!40000 ALTER TABLE `repair` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `STATUS` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2025-01-08 21:34:13','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2025-01-08 21:34:13','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2025-01-08 21:34:13','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2025-01-08 21:34:13','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2025-01-08 21:34:13','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2025-01-08 21:34:13','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2025-01-08 21:34:13','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2025-01-08 21:34:13','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2025-01-08 21:34:13','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2025-01-08 21:34:13','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2025-01-08 21:34:13','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2025-01-08 21:34:13','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2025-01-08 21:34:13','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2025-01-08 21:34:13','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2025-01-08 21:34:13','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2025-01-08 21:34:13','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2025-01-08 21:34:13','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2025-01-08 21:34:13','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2025-01-08 21:34:13','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2025-01-08 21:34:13','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2025-01-08 21:34:13','',NULL,'停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `STATUS` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2025-01-08 21:34:13','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2025-01-08 21:34:13','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2025-01-08 21:34:13','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2025-01-08 21:34:13','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2025-01-08 21:34:13','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2025-01-08 21:34:13','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2025-01-08 21:34:13','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2025-01-08 21:34:13','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2025-01-08 21:34:13','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2025-01-08 21:34:13','',NULL,'登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `QUERY` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `STATUS` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,97,'/system',NULL,'','',1,0,'M','0','0','*:*:*','system','admin','2025-01-02 21:38:43','',NULL,'系统管理目录'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','admin','2025-01-02 21:38:43','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','admin','2025-01-02 21:38:43','',NULL,'菜单管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','admin','2025-01-02 21:38:43','',NULL,'字典管理菜单'),(1000,'用户查询',2021,1,'','','','',1,0,'F','0','0','system:user:query','#','admin','2025-01-02 21:38:43','',NULL,''),(1001,'用户新增',2021,2,'','','','',1,0,'F','0','0','system:user:add','#','admin','2025-01-02 21:38:43','',NULL,''),(1002,'用户修改',2021,3,'','','','',1,0,'F','0','0','system:user:edit','#','admin','2025-01-02 21:38:43','',NULL,''),(1003,'用户删除',2021,4,'','','','',1,0,'F','0','0','system:user:remove','#','admin','2025-01-02 21:38:43','',NULL,''),(1004,'用户导出',2021,5,'','','','',1,0,'F','0','0','system:user:export','#','admin','2025-01-02 21:38:43','',NULL,''),(1005,'用户导入',2021,6,'','','','',1,0,'F','0','0','system:user:import','#','admin','2025-01-02 21:38:43','',NULL,''),(1006,'重置密码',2021,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2025-01-02 21:38:43','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','admin','2025-01-02 21:38:43','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','admin','2025-01-02 21:38:43','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','admin','2025-01-02 21:38:43','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','admin','2025-01-02 21:38:43','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','admin','2025-01-02 21:38:44','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','admin','2025-01-02 21:38:44','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','admin','2025-01-02 21:38:44','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','admin','2025-01-02 21:38:44','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','admin','2025-01-02 21:38:44','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','admin','2025-01-02 21:38:44','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','admin','2025-01-02 21:38:44','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','admin','2025-01-02 21:38:44','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','admin','2025-01-02 21:38:44','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','admin','2025-01-02 21:38:44','',NULL,''),(2000,'用户管理',0,1,'/user',NULL,NULL,'',1,0,'M','0','0',NULL,'user','',NULL,'',NULL,''),(2002,'宿舍管理',0,2,'/dorm',NULL,NULL,'',1,0,'M','0','0',NULL,'build','',NULL,'',NULL,''),(2004,'信息管理',0,3,'/msg',NULL,NULL,'',1,0,'M','0','0',NULL,'message','',NULL,'',NULL,''),(2007,'申请管理',0,4,'/adjust',NULL,NULL,'',1,0,'M','0','0',NULL,'job','',NULL,'',NULL,''),(2012,'访客管理',0,5,'/visitor',NULL,NULL,'',1,0,'M','0','0',NULL,'server','',NULL,'',NULL,''),(2021,'用户管理',2000,1,'list','user/list',NULL,'',1,0,'C','0','0','user:user:list','user','',NULL,'',NULL,''),(12341241,'宿舍学生列表',1889346147291373569,5,'',NULL,NULL,'',1,0,'F','0','0','dorm:user:list','#',NULL,NULL,NULL,NULL,''),(1889345885025738753,'楼宇管理',2002,1,'build','dorm/build',NULL,'',1,0,'C','0','0','dorm:build:list','#',NULL,NULL,NULL,NULL,''),(1889346147291373569,'房间管理',2002,2,'room','dorm/room',NULL,'',1,0,'C','0','0','dorm:room:list','#',NULL,NULL,NULL,NULL,''),(1889346603661008897,'通知管理',2004,1,'notice','message/notice',NULL,'',1,0,'C','0','0','message:notice:list','#',NULL,NULL,NULL,NULL,''),(1889346800596164610,'报修信息',2004,2,'repair','message/repair',NULL,'',1,0,'C','0','0','message:repair:list','#',NULL,NULL,NULL,NULL,''),(1889347443285172226,'申请管理',2007,1,'adjust','adjust/adjust',NULL,'',1,0,'C','0','0','adjust:adjust:list','#',NULL,NULL,NULL,NULL,''),(1889348057075425281,'访客管理',2012,1,'visitor','visitor/visitor',NULL,'',1,0,'C','0','0','visitor:visitor:list','#',NULL,NULL,NULL,NULL,''),(1889348419752697858,'水电管理',0,6,'/order',NULL,NULL,'',1,0,'M','0','0',NULL,'cascader',NULL,NULL,NULL,NULL,''),(1889348541404291074,'订单管理',1889348419752697858,1,'order','order/order',NULL,'',1,0,'C','0','0','order:order:list','#',NULL,NULL,NULL,NULL,''),(1889348827258691586,'卫生检查管理',0,7,'/check',NULL,NULL,'',1,0,'M','0','0',NULL,'checkbox',NULL,NULL,NULL,NULL,''),(1889348907952906242,'卫生检查管理',1889348827258691586,1,'check','check/check',NULL,'',1,0,'C','0','0','check:check:list','#',NULL,NULL,NULL,NULL,''),(1890381463958953986,'楼宇详情',1889345885025738753,1,'',NULL,NULL,'',1,0,'F','0','0','dorm:build:query','#',NULL,NULL,NULL,NULL,''),(1890381489862975489,'新增楼宇',1889345885025738753,2,'',NULL,NULL,'',1,0,'F','0','0','dorm:build:add','#',NULL,NULL,NULL,NULL,''),(1890381489862975789,'编辑楼宇',1889345885025738753,3,'',NULL,NULL,'',1,0,'F','0','0','dorm:build:edit','#',NULL,NULL,NULL,NULL,''),(1890381532871368706,'删除楼宇',1889345885025738753,4,'',NULL,NULL,'',1,0,'F','0','0','dorm:build:remove','#',NULL,NULL,NULL,NULL,''),(1890382390707200001,'房间详情',1889346147291373569,1,'',NULL,NULL,'',1,0,'F','0','0','dorm:room:query','#',NULL,NULL,NULL,NULL,''),(1890382390707200002,'新增房间',1889346147291373569,2,'',NULL,NULL,'',1,0,'F','0','0','dorm:room:add','#',NULL,NULL,NULL,NULL,''),(1890382390707200004,'编辑房间',1889346147291373569,3,'',NULL,NULL,'',1,0,'F','0','0','dorm:room:edit','#',NULL,NULL,NULL,NULL,''),(1890382390707200005,'删除房间',1889346147291373569,4,'',NULL,NULL,'',1,0,'F','0','0','dorm:room:remove','#',NULL,NULL,NULL,NULL,''),(1890382390707200006,'宿舍学生详情',1889346147291373569,6,'',NULL,NULL,'',1,0,'F','0','0','dorm:user:query','#',NULL,NULL,NULL,NULL,''),(1890382390707200007,'添加宿舍学生',1889346147291373569,7,'',NULL,NULL,'',1,0,'F','0','0','dorm:user:add','#',NULL,NULL,NULL,NULL,''),(1890382390707200008,'删除宿舍学生',1889346147291373569,8,'',NULL,NULL,'',1,0,'F','0','0','dorm:user:remove','#',NULL,NULL,NULL,NULL,''),(1890382390707200009,'编辑宿舍学生',1889346147291373569,9,'',NULL,NULL,'',1,0,'F','0','0','dorm:user:edit','#',NULL,NULL,NULL,NULL,''),(1890387647302549501,'通知详情',1889346603661008897,0,'',NULL,NULL,'',1,0,'F','0','0','message:notice:query','#',NULL,NULL,NULL,NULL,''),(1890387647302549506,'删除通知',1889346603661008897,3,'',NULL,NULL,'',1,0,'F','0','0','message:notice:remove','#',NULL,NULL,NULL,NULL,''),(1890387647302549509,'添加通知',1889346603661008897,1,'',NULL,NULL,'',1,0,'F','0','0','message:notice:add','#',NULL,NULL,NULL,NULL,''),(1890387647302549511,'编辑通知',1889346603661008897,2,'',NULL,NULL,'',1,0,'F','0','0','message:notice:edit','#',NULL,NULL,NULL,NULL,''),(1890389113866133506,'申请详情',1889347443285172226,0,'',NULL,NULL,'',1,0,'F','0','0','adjust:adjust:query','#',NULL,NULL,NULL,NULL,''),(1890389135970115585,'新增申请',1889347443285172226,1,'',NULL,NULL,'',1,0,'F','0','0','adjust:adjust:add','#',NULL,NULL,NULL,NULL,''),(1890389162935296001,'编辑申请',1889347443285172226,2,'',NULL,NULL,'',1,0,'F','0','0','adjust:adjust:edit','#',NULL,NULL,NULL,NULL,''),(1890389194107363330,'删除申请',1889347443285172226,3,'',NULL,NULL,'',1,0,'F','0','0','adjust:adjust:remove','#',NULL,NULL,NULL,NULL,''),(1890390429300535298,'维修详情',1889346800596164610,0,'',NULL,NULL,'',1,0,'F','0','0','message:repair:query','#',NULL,NULL,NULL,NULL,''),(1890390458320924673,'新增维修',1889346800596164610,1,'',NULL,NULL,'',1,0,'F','0','0','message:repair:add','#',NULL,NULL,NULL,NULL,''),(1890390480764645378,'编辑维修',1889346800596164610,2,'',NULL,NULL,'',1,0,'F','0','0','message:repair:edit','#',NULL,NULL,NULL,NULL,''),(1890390509441101825,'删除维修',1889346800596164610,3,'',NULL,NULL,'',1,0,'F','0','0','message:repair:remove','#',NULL,NULL,NULL,NULL,''),(1890391646152011777,'访客详情',1889348057075425281,0,'',NULL,NULL,'',1,0,'F','0','0','visitor:visitor:query','#',NULL,NULL,NULL,NULL,''),(1890391667857534977,'新增访客',1889348057075425281,1,'',NULL,NULL,'',1,0,'F','0','0','visitor:visitor:add','#',NULL,NULL,NULL,NULL,''),(1890391695040819202,'编辑访客',1889348057075425281,2,'',NULL,NULL,'',1,0,'F','0','0','visitor:visitor:edit','#',NULL,NULL,NULL,NULL,''),(1890391722173771778,'删除访客',1889348057075425281,3,'',NULL,NULL,'',1,0,'F','0','0','visitor:visitor:remove','#',NULL,NULL,NULL,NULL,''),(1890392527949869058,'订单详情',1889348541404291074,1,'',NULL,NULL,'',1,0,'F','0','0','order:order:query','#',NULL,NULL,NULL,NULL,''),(1890392559616864257,'新增订单',1889348541404291074,2,'',NULL,NULL,'',1,0,'F','0','0','order:order:add','#',NULL,NULL,NULL,NULL,''),(1890392586611404802,'编辑订单',1889348541404291074,22,'',NULL,NULL,'',1,0,'F','0','0','order:order:edit','#',NULL,NULL,NULL,NULL,''),(1890392620564295681,'删除订单',1889348541404291074,22,'',NULL,NULL,'',1,0,'F','0','0','order:order:remove','#',NULL,NULL,NULL,NULL,''),(1890392713308745730,'卫生检查详情',1889348907952906242,444,'',NULL,NULL,'',1,0,'F','0','0','check:check:query','#',NULL,NULL,NULL,NULL,''),(1890392736624881666,'新增卫生检查',1889348907952906242,444,'',NULL,NULL,'',1,0,'F','0','0','check:check:add','#',NULL,NULL,NULL,NULL,''),(1890392761178337282,'编辑卫生检查',1889348907952906242,444,'',NULL,NULL,'',1,0,'F','0','0','check:check:edit','#',NULL,NULL,NULL,NULL,''),(1890392781025783809,'删除卫生检查',1889348907952906242,444,'',NULL,NULL,'',1,0,'F','0','0','check:check:remove','#',NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'欢迎各位同学进入新宿舍','1',_binary '<p>欢迎各位同学进入新宿舍! 大家根据自己需要选择合适的房位。</p>','0','',NULL,'','2025-02-14 21:54:15',NULL),(2,'需要保修设备的同学联系宿管！','2',_binary '<p>需要保修设备的同学联系宿管！</p>','0','',NULL,'',NULL,NULL),(3,'关于放假通知','2',_binary '<p>元旦起，1.1~1.3放假三天。请同学们合理安排时间。</p>','0','',NULL,'',NULL,NULL);
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `STATUS` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'系统管理人员','admin',1,'0','0','1','0','2025-01-08 21:34:08','admin','2025-01-08 21:34:08'),(2,'宿舍管理员','dorm_admin',2,'0','0','1','0','2025-01-08 21:34:08','admin','2025-01-08 21:34:08'),(1889906932656844801,'学生','student',0,'0','0',NULL,NULL,'2025-01-08 21:34:08',NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1),(1,101),(1,102),(1,105),(1,1000),(1,1001),(1,1002),(1,1003),(1,1004),(1,1005),(1,1006),(1,1007),(1,1008),(1,1009),(1,1010),(1,1011),(1,1012),(1,1013),(1,1014),(1,1015),(1,1025),(1,1026),(1,1027),(1,1028),(1,1029),(1,2000),(1,2002),(1,2004),(1,2007),(1,2012),(1,2021),(1,12341241),(1,1889345885025738753),(1,1889346147291373569),(1,1889346603661008897),(1,1889346800596164610),(1,1889347443285172226),(1,1889348057075425281),(1,1889348419752697858),(1,1889348541404291074),(1,1889348827258691586),(1,1889348907952906242),(1,1890381463958953986),(1,1890381489862975489),(1,1890381489862975789),(1,1890381532871368706),(1,1890382390707200001),(1,1890382390707200002),(1,1890382390707200004),(1,1890382390707200005),(1,1890382390707200006),(1,1890382390707200007),(1,1890382390707200008),(1,1890382390707200009),(1,1890387647302549501),(1,1890387647302549506),(1,1890387647302549509),(1,1890387647302549511),(1,1890389113866133506),(1,1890389135970115585),(1,1890389162935296001),(1,1890389194107363330),(1,1890390429300535298),(1,1890390458320924673),(1,1890390480764645378),(1,1890390509441101825),(1,1890391646152011777),(1,1890391667857534977),(1,1890391695040819202),(1,1890391722173771778),(1,1890392527949869058),(1,1890392559616864257),(1,1890392586611404802),(1,1890392620564295681),(1,1890392713308745730),(1,1890392736624881666),(1,1890392761178337282),(1,1890392781025783809),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,2012),(2,1889346147291373569),(2,1889348057075425281),(2,1889348827258691586),(2,1889348907952906242),(2,1890391646152011777),(2,1890391667857534977),(2,1890391695040819202),(2,1890391722173771778),(2,1890392713308745730),(2,1890392736624881666),(2,1890392761178337282),(2,1890392781025783809),(1889906932656844801,2002),(1889906932656844801,2004),(1889906932656844801,2007),(1889906932656844801,2012),(1889906932656844801,12341241),(1889906932656844801,1889345885025738753),(1889906932656844801,1889346147291373569),(1889906932656844801,1889346603661008897),(1889906932656844801,1889346800596164610),(1889906932656844801,1889347443285172226),(1889906932656844801,1889348057075425281),(1889906932656844801,1889348419752697858),(1889906932656844801,1889348541404291074),(1889906932656844801,1890381463958953986),(1889906932656844801,1890382390707200001),(1889906932656844801,1890387647302549501),(1889906932656844801,1890389113866133506),(1889906932656844801,1890389135970115585),(1889906932656844801,1890390429300535298),(1889906932656844801,1890390458320924673),(1889906932656844801,1890391667857534977),(1889906932656844801,1890392527949869058),(1889906932656844801,1890392559616864257);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `account` varchar(30) NOT NULL COMMENT '用户账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户名称',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `PASSWORD` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','admin','00','00','123@qq.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-02-04 17:28:51.000000 +00:00',NULL,'2025-01-08 21:34:08.000000 +00:00',NULL),(4,'1002','王五同学','五五','00','wangwu@example.com','13700337000','0','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','','admin','2024-02-13 12:30:00','',NULL),(5,'1003','赵六（宿管）','六哥','00','zhaoliu@example.com','13500435000','0','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','','admin','2024-02-13 12:40:00',NULL,NULL),(6,'user05','孙七','七仔','00','sunqi@example.com','13400534000','2','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 12:50:00','',NULL),(7,'user06','周八','八爷','00','zhouba@example.com','13300633000','0','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:00:00','',NULL),(8,'user07','吴九','九妹','00','wujiu@example.com','13200732000','1','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:10:00','',NULL),(9,'user08','郑十','十仔','00','zhengshi@example.com','13100831000','0','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:20:00','',NULL),(10,'user09','冯十一','小冯','00','fengshiyi@example.com','13000930000','2','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:30:00',NULL,NULL),(11,'user10','陈十二','十二哥','00','chen12@example.com','15001015000','0','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:40:00','',NULL),(12,'user11','朱十三','小朱','00','zhu13@example.com','15101115100','1','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 13:50:00','',NULL),(13,'user12','林十四','林哥','00','lin14@example.com','15201215200','0','','e10adc3949ba59abbe56e057f20f883e','1','0','','admin','2024-02-13 14:00:00','',NULL),(14,'user13','徐十五','阿徐','00','xu15@example.com','15301315300','2','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 14:10:00','',NULL),(15,'user14','郭十六','郭弟','00','guo16@example.com','15401415400','1','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 14:20:00','',NULL),(16,'user15','韩十七','韩妹','00','han17@example.com','15501515500','1','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 14:30:00','',NULL),(17,'user16','高十八','高高','00','gao18@example.com','15601615600','0','','e10adc3949ba59abbe56e057f20f883e','1','0','','admin','2024-02-13 14:40:00','',NULL),(18,'user17','何十九','何叔','00','he19@example.com','15701715700','0','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 14:50:00','',NULL),(19,'user18','梁二十','小梁','00','liang20@example.com','15801815800','2','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 15:00:00','',NULL),(20,'user19','罗二一','罗哥','00','luo21@example.com','15901915900','1','','e10adc3949ba59abbe56e057f20f883e','1','0','','admin','2024-02-13 15:10:00','',NULL),(112,'user01','张三','小三','00','zhangsan@example.com','13900139000','0','','e10adc3949ba59abbe56e057f20f883e','0','0','','admin','2024-02-13 12:10:00','',NULL),(1890378143282339841,'user1900','user1900','user1900','00','12321@qq.com','13600000002','0','','$2a$10$irpIGOyCc2BcBd4w0eAPhOQ2s74wPEwrlCvOZcm9a/YzwitdB1cJG','0','0',NULL,NULL,NULL,NULL,NULL),(1890378895245549569,'user1009','学生1号','学生1号','00','12312@qq.com','13600000003','0','','$2a$10$OSuXfl0F4yJlkOMAb.MAJeSsfqomSPQCSfEfHq4YQJgb2/9VHsUFW','0','0',NULL,NULL,NULL,NULL,NULL),(1890379474285993985,'1001','学生2号','学生1号','00','112312@qq.com','13600000004','1','','$2a$10$/H8aSYp2hCvDTD9vJZl9ZeDTIGuAU7nmu08Nx7S49.kHDetsH4cWW','0','0',NULL,NULL,NULL,NULL,NULL),(1890397626654797825,'2001','2001','2001','00','','','0','','$2a$10$UX25tG.ev33wa6CNn3X7uOehx6IzuqcH.zbIoohuHLEhGCknHqGRW','0','0',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(4,1889906932656844801),(5,2),(6,1889906932656844801),(8,1889906932656844801),(9,2),(9,1889906932656844801),(10,1889906932656844801),(1890378143282339841,1889906932656844801),(1890378895245549569,1889906932656844801),(1890379474285993985,1889906932656844801);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor`
--

DROP TABLE IF EXISTS `visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitor` (
  `id` bigint NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `gender` int NOT NULL DEFAULT '0' COMMENT '性别',
  `phone_num` varchar(255) NOT NULL COMMENT '电话',
  `origin_city` varchar(255) NOT NULL COMMENT '来源城市',
  `visit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '来访时间',
  `content` varchar(255) NOT NULL COMMENT '事情',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='到访表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor`
--

LOCK TABLES `visitor` WRITE;
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` VALUES (1889688849057026050,'1234',1,'13600000001','广州','2025-02-12 08:00:00','<p>154685</p>',NULL,NULL,'2025-02-12 14:51:45',NULL,'2025-02-13 12:48:45');
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-14 22:09:53
