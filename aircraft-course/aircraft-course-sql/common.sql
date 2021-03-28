-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.20 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 iab-admin 的数据库结构
DROP DATABASE IF EXISTS `aircraft-course`;
CREATE DATABASE IF NOT EXISTS `aircraft-course` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aircraft-course`;


-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '部门主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
  `short_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门简称',
  `manager_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '部门负责人id',
  `parent_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '部门的父级id',
  `sort` int(11) NOT NULL COMMENT '部门排序',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, '华资', 'ZWGWL', 16, 0, 1, '2019-04-03 10:41:25', '2019-04-03 10:41:25');
INSERT INTO `sys_department` VALUES (2, '社保事业部', NULL, 15, 1, 17, '2019-04-15 16:45:10', '2019-04-15 16:45:10');
INSERT INTO `sys_department` VALUES (4, '公共研发中心', '管理', 14, 1, 20, '2019-04-17 16:14:55', '2019-04-17 16:14:55');
INSERT INTO `sys_department` VALUES (8, '销售部门', NULL, NULL, 4, 8, '2019-04-25 12:25:52', '2019-04-25 12:25:52');
INSERT INTO `sys_department` VALUES (9, '四级部门-1', NULL, NULL, 8, 9, '2019-04-25 12:26:36', '2019-04-25 12:26:36');
INSERT INTO `sys_department` VALUES (10, '五级部门-1', NULL, NULL, 9, 10, '2019-04-25 12:26:49', '2019-04-25 12:26:49');
INSERT INTO `sys_department` VALUES (11, '六级部门-1', NULL, NULL, 10, 11, '2019-04-25 12:26:59', '2019-04-25 12:26:59');
INSERT INTO `sys_department` VALUES (12, '七级部门-1', NULL, NULL, 11, 12, '2019-04-25 12:27:18', '2019-04-25 12:27:18');
INSERT INTO `sys_department` VALUES (13, '八级部门-1', NULL, NULL, 12, 13, '2019-04-25 12:27:34', '2019-04-25 12:27:34');
INSERT INTO `sys_department` VALUES (14, '九级部门-1', NULL, NULL, 13, 14, '2019-04-25 12:27:47', '2019-04-25 12:27:47');
INSERT INTO `sys_department` VALUES (15, '十级部门-1', NULL, NULL, 14, 15, '2019-04-25 12:28:16', '2019-04-25 12:28:16');
INSERT INTO `sys_department` VALUES (16, '十一级部门部门部部门门嘻嘻哈哈-1', NULL, 13, 15, 16, '2019-04-25 14:56:40', '2019-04-25 14:56:40');
INSERT INTO `sys_department` VALUES (17, 'IAB事业部', NULL, 16, 1, 4, '2019-04-26 11:53:50', '2019-04-26 11:53:50');
INSERT INTO `sys_department` VALUES (18, '开发小组一', NULL, 16, 17, 18, '2019-04-26 11:54:06', '2019-04-26 11:54:06');
INSERT INTO `sys_department` VALUES (19, '售前', NULL, NULL, 2, 22, '2019-04-26 14:36:18', '2019-04-26 14:36:18');
INSERT INTO `sys_department` VALUES (20, '开发部门', NULL, NULL, 2, 23, '2019-04-26 14:36:28', '2019-04-26 14:36:28');
INSERT INTO `sys_department` VALUES (22, '其他事业部', NULL, 16, 1, 2, '2019-04-28 14:21:44', '2019-04-28 14:21:44');
INSERT INTO `sys_department` VALUES (23, '开发部门', NULL, 22, 4, 19, '2019-04-28 14:22:48', '2019-04-28 14:22:48');
INSERT INTO `sys_department` VALUES (24, '测试', NULL, 18, 23, 24, '2019-04-29 10:12:42', '2019-04-29 10:12:42');
INSERT INTO `sys_department` VALUES (25, '测试', NULL, 18, 23, 25, '2019-04-29 10:12:42', '2019-04-29 10:12:42');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数名字',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数key',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `config_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数类别',
  `is_using` int(11) NOT NULL COMMENT '是否使用0 否 1 是',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上次修改时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES (1, '超级管理员', 'user_superman', '12,13,1', 'employee', 1, '123r8566456', '2021-03-16 15:23:31', '2018-08-18 16:28:03');
INSERT INTO `sys_param` VALUES (13, '本地上传URL前缀', 'local_upload_url_prefix', 'http://172.16.0.145/smartAdmin/file/', 'upload', 1, '', '2019-09-04 16:23:49', '2019-04-26 17:06:53');
INSERT INTO `sys_param` VALUES (14, '阿里云上传配置', 'ali_oss', '{\"accessKeyId\":\"\",\"accessKeySecret\":\"\",\"bucketName\":\"sit\",\"endpoint\":\"http://oss-cn-beijing.aliyuncs.com\"}', 'upload', 1, 'eefwfwfds', '2019-11-16 18:04:30', '2019-05-11 18:00:06');
INSERT INTO `sys_param` VALUES (15, '邮件发配置', 'email_config', '{\"password\":\"smartadmin\",\"smtpHost\":\"smtp.163.com\",\"username\":\"smartadmin1024@163.com\"}', 'email', 1, NULL, '2019-09-04 16:42:17', '2019-05-13 16:57:48');
INSERT INTO `sys_param` VALUES (16, '七牛云上传配置', 'qi_niu_oss', '{\"accessKeyId\":\"rX7HgY1ZLpUD25JrA-uwMM_jj-\",\"accessKeySecret\":\"\",\"bucketName\":\"sun-smart-admin\",\"endpoint\":\"http://puvpyay08.bkt.clouddn.com\"}', 'upload', 1, NULL, '2019-11-16 18:04:42', '2019-07-19 16:05:56');
INSERT INTO `sys_param` VALUES (17, 'test', 'ww_1', 'ewr', '3', 1, 'testoo', '2019-11-08 09:43:36', '2019-11-08 09:27:19');
INSERT INTO `sys_param` VALUES (18, '4234', '42342', '423423', '23423', 1, '423423111111111111111111111111111111111111423423111111111111111111111111111111111111423423111111111111111111111111111111111111423423111111111111111111111111111111111111423423111111111111111111111111111111111111', '2019-11-14 14:58:39', '2019-11-14 11:22:49');
INSERT INTO `sys_param` VALUES (19, 'test323@', 'test', '123456', '11_', 1, 'gggggg', '2019-11-15 16:24:52', '2019-11-15 16:24:52');

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `position_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位描述',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES (1, 'java develop', 'java develop is good job', '2019-07-03 15:18:45', '2019-07-03 15:18:45');
INSERT INTO `sys_position` VALUES (2, 'android develop', 'android develop is good job', '2019-07-04 16:11:11', '2019-07-04 16:11:00');
INSERT INTO `sys_position` VALUES (3, '测试岗位1', '这是内容11', '2019-09-02 16:39:33', '2019-07-10 14:03:50');
INSERT INTO `sys_position` VALUES (8, '测试岗位2', '测试岗位2.。', '2019-09-04 10:19:40', '2019-09-04 10:19:32');
INSERT INTO `sys_position` VALUES (13, '测试岗位7', '测试岗位7', '2019-09-05 14:40:03', '2019-09-05 14:40:03');
INSERT INTO `sys_position` VALUES (14, '测试岗位8', '测试岗位8', '2019-09-05 14:40:09', '2019-09-05 14:40:09');
INSERT INTO `sys_position` VALUES (15, '测试岗位9', '测试岗位9', '2019-09-05 14:40:19', '2019-09-05 14:40:19');
INSERT INTO `sys_position` VALUES (16, 'aaa22222', 'ddddddddddd', '2019-11-15 17:04:29', '2019-11-06 15:58:37');

-- ----------------------------
-- Table structure for sys_position_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_position_relation`;
CREATE TABLE `sys_position_relation`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `position_id` int(11) NULL DEFAULT NULL COMMENT '岗位ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '员工ID',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `job_id`(`position_id`) USING BTREE,
  INDEX `employee_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position_relation
-- ----------------------------
INSERT INTO `sys_position_relation` VALUES (14, 1, 28, '2019-07-10 16:40:14', '2019-07-10 16:40:14');
INSERT INTO `sys_position_relation` VALUES (18, 1, 29, '2019-07-11 10:18:22', '2019-07-11 10:18:22');
INSERT INTO `sys_position_relation` VALUES (19, 3, 29, '2019-07-11 10:18:22', '2019-07-11 10:18:22');
INSERT INTO `sys_position_relation` VALUES (20, 2, 29, '2019-07-11 10:18:22', '2019-07-11 10:18:22');
INSERT INTO `sys_position_relation` VALUES (21, 1, 30, '2019-08-08 14:35:51', '2019-08-08 14:35:51');
INSERT INTO `sys_position_relation` VALUES (22, 2, 30, '2019-08-08 14:35:51', '2019-08-08 14:35:51');
INSERT INTO `sys_position_relation` VALUES (23, 3, 30, '2019-08-08 14:35:51', '2019-08-08 14:35:51');
INSERT INTO `sys_position_relation` VALUES (26, 2, 31, '2019-08-23 09:26:44', '2019-08-23 09:26:44');
INSERT INTO `sys_position_relation` VALUES (27, 3, 31, '2019-08-23 09:26:44', '2019-08-23 09:26:44');
INSERT INTO `sys_position_relation` VALUES (28, 3, 32, '2019-09-04 09:05:47', '2019-09-04 09:05:47');
INSERT INTO `sys_position_relation` VALUES (29, 2, 32, '2019-09-04 09:05:47', '2019-09-04 09:05:47');
INSERT INTO `sys_position_relation` VALUES (30, 3, 22, '2019-09-04 09:06:46', '2019-09-04 09:06:46');
INSERT INTO `sys_position_relation` VALUES (31, 2, 22, '2019-09-04 09:06:46', '2019-09-04 09:06:46');
INSERT INTO `sys_position_relation` VALUES (35, 8, 35, '2019-09-04 15:09:00', '2019-09-04 15:09:00');
INSERT INTO `sys_position_relation` VALUES (36, 3, 35, '2019-09-04 15:09:00', '2019-09-04 15:09:00');
INSERT INTO `sys_position_relation` VALUES (37, 15, 23, '2019-09-05 16:13:02', '2019-09-05 16:13:02');
INSERT INTO `sys_position_relation` VALUES (38, 14, 23, '2019-09-05 16:13:02', '2019-09-05 16:13:02');
INSERT INTO `sys_position_relation` VALUES (39, 13, 23, '2019-09-05 16:13:02', '2019-09-05 16:13:02');
INSERT INTO `sys_position_relation` VALUES (40, 3, 34, '2019-09-06 08:55:18', '2019-09-06 08:55:18');
INSERT INTO `sys_position_relation` VALUES (41, 2, 34, '2019-09-06 08:55:18', '2019-09-06 08:55:18');
INSERT INTO `sys_position_relation` VALUES (42, 1, 34, '2019-09-06 08:55:18', '2019-09-06 08:55:18');
INSERT INTO `sys_position_relation` VALUES (43, 14, 36, '2019-09-09 17:01:39', '2019-09-09 17:01:39');
INSERT INTO `sys_position_relation` VALUES (44, 3, 37, '2019-11-08 09:32:39', '2019-11-08 09:32:39');
INSERT INTO `sys_position_relation` VALUES (46, 8, 38, '2019-11-14 16:08:05', '2019-11-14 16:08:05');
INSERT INTO `sys_position_relation` VALUES (50, 16, 39, '2019-11-15 17:07:04', '2019-11-15 17:07:04');
INSERT INTO `sys_position_relation` VALUES (51, 13, 39, '2019-11-15 17:07:04', '2019-11-15 17:07:04');
INSERT INTO `sys_position_relation` VALUES (52, 14, 39, '2019-11-15 17:07:04', '2019-11-15 17:07:04');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '功能权限表主键id',
  `type` tinyint(4) NOT NULL COMMENT '1.菜单（菜单权限） 2.功能点(接口权限)',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `key` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路由name 英文关键字（全表唯一）',
  `url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '路由path/type=3为API接口',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `parent_key` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级key',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`key`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `parent_key`(`parent_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, 1, '用户管理', 'UserManager', '/user', 20, 'System', '2021-03-16 16:20:21', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (2, 1, '角色管理', 'RoleManager', '/user/role', 21, 'UserManager', '2021-03-16 16:22:21', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (3, 1, '岗位管理', 'PositionList', '/user/position', 22, 'UserManager', '2021-03-16 16:22:23', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (4, 1, '员工管理', 'RoleUserManager', '/user/role-user-manager', 23, 'UserManager', '2021-03-16 16:25:27', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (5, 1, '系统设置', 'SystemSetting', '/system-setting', 29, 'System', '2020-12-14 15:16:26', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (6, 1, '系统参数', 'SysParam', '/system-setting/system-param', 30, 'SystemSetting', '2021-03-22 16:22:32', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (8, 1, '菜单设置', 'SystemResource', '/system-setting/system-resource', 31, 'SystemSetting', '2021-03-16 16:25:19', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (15, 1, '用户日志', 'UserLog', '/user-log', 26, 'System', '2020-12-14 15:16:26', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (16, 1, '用户操作日志', 'UserOperateLog', '/user-log/user-operate-log', 27, 'UserLog', '2020-12-14 15:16:26', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (17, 1, '用户登录日志', 'UserLoginLog', '/user-log/user-login-log', 28, 'UserLog', '2020-12-14 15:16:26', '2019-11-01 11:28:07');
INSERT INTO `sys_resource` VALUES (39, 2, '添加角色', 'add-role', 'roleController.addRole', 0, 'RoleManager', '2021-03-16 16:25:04', '2019-11-01 11:47:29');
INSERT INTO `sys_resource` VALUES (40, 2, '删除角色', 'delete-role', 'roleController.deleteRole', 1, 'RoleManager', '2021-03-16 16:25:04', '2019-11-01 11:47:43');
INSERT INTO `sys_resource` VALUES (41, 2, '编辑角色', 'update-role', 'roleController.updateRole', 2, 'RoleManager', '2021-03-16 16:25:04', '2019-11-01 11:47:55');
INSERT INTO `sys_resource` VALUES (42, 2, '修改角色权限', 'update-role-resource', 'roleResourceController.updateRoleResource', 3, 'RoleManager', '2021-03-16 16:32:57', '2019-11-01 11:48:09');
INSERT INTO `sys_resource` VALUES (43, 2, '添加成员', 'add-user-role', 'roleUserController.addUserList', 4, 'RoleManager', '2021-03-16 17:47:05', '2019-11-05 10:38:11');
INSERT INTO `sys_resource` VALUES (44, 2, '查询成员', 'search-user-list', 'roleUserController.listAlluUserRoleId,roleUserController.listUserByName,roleController.getAllRole,roleResourceController.listResourceByRoleId', 7, 'RoleManager', '2021-03-16 16:47:43', '2019-11-05 10:39:04');
INSERT INTO `sys_resource` VALUES (45, 2, '移除成员', 'delete-user-role', 'roleUserController.removeUser', 5, 'RoleManager', '2021-03-16 17:39:55', '2019-11-05 10:40:09');
INSERT INTO `sys_resource` VALUES (46, 2, '批量移除', 'delete-user-role-batch', 'roleUserController.removeUserList', 6, 'RoleManager', '2021-03-16 17:47:09', '2019-11-05 10:40:27');
INSERT INTO `sys_resource` VALUES (47, 2, '查询数据范围', 'query-data-scope', 'dataScopeController.dataScopeList,dataScopeController.dataScopeListByRole,roleResourceController.listResourceByRoleId,resourceController.queryAll,resourceController.getAllUrl', 8, 'RoleManager', '2021-03-16 16:47:05', '2019-11-05 10:40:57');
INSERT INTO `sys_resource` VALUES (48, 2, '更新数据范围', 'update-data-scope', 'dataScopeController.dataScopeBatchSet', 9, 'RoleManager', '2021-03-16 16:24:59', '2019-11-05 10:41:03');
INSERT INTO `sys_resource` VALUES (49, 2, '查询', 'search-position', 'PositionController.queryJobById,PositionController.getJobPage', 0, 'PositionList', '2021-03-22 15:39:38', '2019-11-05 10:41:30');
INSERT INTO `sys_resource` VALUES (50, 2, '添加', 'add-position', 'PositionController.addJob', 1, 'PositionList', '2021-03-22 15:39:28', '2019-11-05 10:41:40');
INSERT INTO `sys_resource` VALUES (51, 2, '修改', 'update-position', 'PositionController.updateJob', 2, 'PositionList', '2021-03-22 15:39:30', '2019-11-05 10:41:49');
INSERT INTO `sys_resource` VALUES (52, 2, '删除', 'delete-position', 'PositionController.removeJob', 3, 'PositionList', '2021-03-22 15:39:32', '2019-11-05 10:41:57');
INSERT INTO `sys_resource` VALUES (53, 2, '添加部门', 'add-department', 'departmentController.addDepartment', 0, 'RoleUserManager', '2021-03-16 16:26:55', '2019-11-05 11:11:18');
INSERT INTO `sys_resource` VALUES (54, 2, '编辑部门', 'update-department', 'departmentController.updateDepartment', 1, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 11:11:29');
INSERT INTO `sys_resource` VALUES (55, 2, '删除部门', 'delete-department', 'departmentController.delDepartment', 2, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 11:11:48');
INSERT INTO `sys_resource` VALUES (56, 2, '查询', 'search-department', 'departmentController.listAll,departmentController.getDepartment,departmentController.listDepartmentUser,departmentController.listDepartment,userController.query', 3, 'RoleUserManager', '2021-03-16 16:51:40', '2019-11-05 11:12:09');
INSERT INTO `sys_resource` VALUES (57, 2, '添加成员', 'add-user', 'userController.addUser', 4, 'RoleUserManager', '2021-03-16 16:41:57', '2019-11-05 17:06:23');
INSERT INTO `sys_resource` VALUES (58, 2, '编辑成员', 'update-user', 'userController.updateUser', 5, 'RoleUserManager', '2021-03-16 16:41:50', '2019-11-05 17:06:57');
INSERT INTO `sys_resource` VALUES (59, 2, '禁用', 'disabled-user', 'userController.updateStatus', 6, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 17:14:35');
INSERT INTO `sys_resource` VALUES (60, 2, '批量操作', 'disabled-user-batch', 'userController.batchUpdateStatus', 7, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 17:19:23');
INSERT INTO `sys_resource` VALUES (61, 2, '员工角色编辑', 'update-user-role', 'userController.updateRoles', 8, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 17:21:15');
INSERT INTO `sys_resource` VALUES (62, 2, '重置密码', 'reset-user-password', 'userController.resetPasswd', 10, 'RoleUserManager', '2021-03-16 16:26:58', '2019-11-05 17:22:13');
INSERT INTO `sys_resource` VALUES (63, 2, '删除员工', 'delete-user', 'userController.deleteUserById', 9, 'RoleUserManager', '2021-03-16 16:41:40', '2019-11-05 17:22:27');
INSERT INTO `sys_resource` VALUES (64, 2, '查询系统参数', 'system-params-search', 'SysParamController.selectByKey,SysParamController.getListByGroup,SysParamController.getSysParamPage', 0, 'SysParam', '2021-03-22 16:20:12', '2019-11-05 17:23:41');
INSERT INTO `sys_resource` VALUES (65, 2, '添加系统参数', 'system-params-add', 'SysParamController.addSysParam', 1, 'SysParam', '2021-03-22 16:19:46', '2019-11-05 17:26:00');
INSERT INTO `sys_resource` VALUES (66, 2, '修改系统参数', 'system-params-update', 'SysParamController.updateSysParam', 2, 'SysParam', '2021-03-22 16:19:49', '2019-11-05 17:26:07');
INSERT INTO `sys_resource` VALUES (67, 2, '搜索系统参数', 'system-config-search', 'SysParamController.selectByKey,SysParamController.getListByGroup,SysParamController.getSysParamPage', 3, 'SysParam', '2021-03-22 16:20:01', '2019-11-05 17:26:44');
INSERT INTO `sys_resource` VALUES (69, 2, '编辑', 'resource-main-update', 'ResourceController.menuBatchSave,ResourceController.functionSaveOrUpdate', 1, 'SystemResource', '2021-03-22 16:19:26', '2019-11-05 17:27:28');
INSERT INTO `sys_resource` VALUES (70, 2, '查询', 'resource-main-search', 'ResourceController.queryAll,ResourceController.getAllUrl,ResourceController.functionQuery', 2, 'SystemResource', '2021-03-22 16:19:19', '2019-11-05 17:28:45');
INSERT INTO `sys_resource` VALUES (84, 2, '查询', 'user-operate-log-search', 'userOperateLogController.queryByPage', 0, 'UserOperateLog', '2019-11-06 11:15:04', '2019-11-06 11:15:04');
INSERT INTO `sys_resource` VALUES (85, 2, '详情', 'user-operate-log-detail', 'userOperateLogController.detail', 1, 'UserOperateLog', '2019-11-06 11:15:16', '2019-11-06 11:15:16');
INSERT INTO `sys_resource` VALUES (86, 2, '删除', 'user-operate-log-delete', 'userOperateLogController.delete', 2, 'UserOperateLog', '2019-11-06 11:15:25', '2019-11-06 11:15:25');
INSERT INTO `sys_resource` VALUES (87, 2, '查询', 'user-login-log-search', 'userLoginLogController.queryByPage', 0, 'UserLoginLog', '2019-11-06 11:15:43', '2019-11-06 11:15:43');
INSERT INTO `sys_resource` VALUES (88, 2, '删除', 'user-login-log-delete', 'userLoginLogController.delete', 1, 'UserLoginLog', '2019-11-06 11:15:49', '2019-11-06 11:15:49');
INSERT INTO `sys_resource` VALUES (99, 2, '查询', 'smart-reload-search', 'smartReloadController.listAllReloadItem', 0, 'SmartReloadList', '2019-11-06 11:18:06', '2019-11-06 11:18:06');
INSERT INTO `sys_resource` VALUES (100, 2, '执行reload', 'smart-reload-update', 'smartReloadController.updateByTag', 1, 'SmartReloadList', '2019-11-06 11:18:14', '2019-11-06 11:18:14');
INSERT INTO `sys_resource` VALUES (101, 2, '查看执行结果', 'smart-reload-result', 'smartReloadController.queryReloadResult', 2, 'SmartReloadList', '2019-11-06 11:18:19', '2019-11-06 11:18:19');
INSERT INTO `sys_resource` VALUES (106, 1, '业务功能', 'Business', '/business', 0, NULL, '2020-12-14 15:16:26', '2020-12-14 15:16:26');
INSERT INTO `sys_resource` VALUES (112, 1, '系统设置', 'System', '/system', 19, NULL, '2020-12-14 15:16:26', '2020-12-14 15:16:26');
INSERT INTO `sys_resource` VALUES (126, 2, '批量保存功能点', 'resource-batch-save-points', 'ResourceController.functionSaveOrUpdate', 1, 'SystemResource', '2021-03-22 16:18:59', '2020-12-14 15:17:11');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '', '2019-06-21 12:09:34', '2019-06-21 12:09:34');
INSERT INTO `sys_role` VALUES (34, '销售', '', '2019-08-30 09:30:50', '2019-08-30 09:30:50');
INSERT INTO `sys_role` VALUES (37, '财务', '', '2019-08-30 09:31:16', '2019-08-30 09:31:16');
INSERT INTO `sys_role` VALUES (38, '运营', '', '2019-08-30 09:31:22', '2019-08-30 09:31:22');
INSERT INTO `sys_role` VALUES (40, '测试角色1', '测试角色1', '2019-09-05 15:05:38', '2019-09-05 15:05:38');
INSERT INTO `sys_role` VALUES (42, '测试角色3', '测试角色3', '2019-09-05 15:05:49', '2019-09-05 15:05:49');
INSERT INTO `sys_role` VALUES (45, '测试角色6', '测试角色6', '2019-09-05 15:06:06', '2019-09-05 15:06:06');

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_scope_type` int(11) NOT NULL COMMENT '数据范围id',
  `view_type` int(11) NOT NULL COMMENT '数据范围类型（0：本人，1：本组织机构，2：本组织机构已经子组织机构，3：全部）',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------
INSERT INTO `sys_role_data_scope` VALUES (5, 0, 2, 9, '2019-04-29 15:01:04', '2019-04-29 15:01:04');
INSERT INTO `sys_role_data_scope` VALUES (14, 0, 2, 40, '2019-09-05 15:25:37', '2019-09-05 15:25:37');
INSERT INTO `sys_role_data_scope` VALUES (23, 0, 3, 1, '2021-03-16 15:36:24', '2021-03-16 15:36:24');
INSERT INTO `sys_role_data_scope` VALUES (27, 0, 3, 34, '2021-03-22 16:12:43', '2021-03-22 16:12:43');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `privilege_key` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限key',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11445 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色权限功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES (3586, 45, 'add-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3587, 45, 'delete-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3588, 45, 'update-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3589, 45, 'update-role-privilege', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3590, 45, 'add-employee-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3591, 45, 'delete-employee-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3592, 45, 'delete-employee-role-batch', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3593, 45, 'search-employee-list', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3597, 45, 'search-position', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3598, 45, 'add-position', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3599, 45, 'update-position', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3600, 45, 'delete-position', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3602, 45, 'add-department', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3603, 45, 'set-employee-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3604, 45, 'update-department', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3605, 45, 'delete-department', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3606, 45, 'search-department', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3607, 45, 'add-employee', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3608, 45, 'update-employee', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3609, 45, 'disabled-employee', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3610, 45, 'disabled-employee-batch', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3611, 45, 'update-employee-role', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3612, 45, 'delete-employee', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (3613, 45, 'reset-employee-password', '2019-09-06 15:28:17', '2019-09-06 15:28:17');
INSERT INTO `sys_role_resource` VALUES (9674, 38, 'Employee', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9675, 38, 'PositionList', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9676, 38, 'SystemSetting', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9677, 38, 'SystemConfig', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9683, 38, 'Monitor', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9691, 38, 'File', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9692, 38, 'FileList', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9693, 38, 'search-position', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9694, 38, 'system-params-search', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9695, 38, 'system-config-update', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9696, 38, 'system-config-search', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9704, 38, 'file-filePage-query', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (9705, 38, 'file-filePage-upload', '2019-11-15 16:53:47', '2019-11-15 16:53:47');
INSERT INTO `sys_role_resource` VALUES (10585, 40, 'Employee', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10586, 40, 'RoleManage', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10587, 40, 'PositionList', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10588, 40, 'RoleEmployeeManage', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10589, 40, 'SystemSetting', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10590, 40, 'SystemConfig', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10591, 40, 'SystemPrivilege', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10599, 40, 'add-role', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10600, 40, 'delete-role', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10601, 40, 'update-role', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10602, 40, 'update-role-privilege', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10603, 40, 'add-employee-role', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10604, 40, 'search-employee-list', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10605, 40, 'delete-employee-role', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10606, 40, 'delete-employee-role-batch', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10607, 40, 'query-data-scope', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10608, 40, 'update-data-scope', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10609, 40, 'search-position', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10610, 40, 'add-position', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10611, 40, 'update-position', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10612, 40, 'search-department', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10613, 40, 'system-params-add', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10614, 40, 'system-config-search', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10615, 40, 'privilege-main-search', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10623, 40, 'email-send', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (10630, 40, 'delete-department', '2019-11-15 17:19:42', '2019-11-15 17:19:42');
INSERT INTO `sys_role_resource` VALUES (11427, 37, 'SystemSetting', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11428, 37, 'SystemConfig', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11429, 37, 'SystemPrivilege', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11430, 37, 'system-params-add', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11431, 37, 'system-config-update', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11432, 37, 'system-config-search', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11433, 37, 'privilege-main-update', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11434, 37, 'privilege-main-search', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11435, 37, 'System', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11436, 37, 'privilege-batch-save-points', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11437, 37, 'system-params-search', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11438, 37, 'UserLog', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11439, 37, 'UserOperateLog', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11440, 37, 'user-operate-log-search', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11441, 37, 'user-operate-log-detail', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11442, 37, 'user-operate-log-delete', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11443, 37, 'UserLoginLog', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11444, 37, 'user-login-log-search', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11445, 37, 'user-login-log-delete', '2021-03-16 15:45:19', '2021-03-16 15:45:19');
INSERT INTO `sys_role_resource` VALUES (11491, 1, 'UserManager', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11492, 1, 'RoleManager', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11493, 1, 'PositionList', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11494, 1, 'RoleUserManager', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11495, 1, 'UserLog', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11496, 1, 'UserOperateLog', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11497, 1, 'UserLoginLog', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11498, 1, 'add-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11499, 1, 'delete-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11500, 1, 'update-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11501, 1, 'update-role-resource', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11502, 1, 'add-user-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11503, 1, 'search-user-list', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11504, 1, 'delete-user-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11505, 1, 'delete-user-role-batch', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11506, 1, 'query-data-scope', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11507, 1, 'update-data-scope', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11508, 1, 'search-position', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11509, 1, 'add-position', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11510, 1, 'update-position', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11511, 1, 'delete-position', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11512, 1, 'add-department', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11513, 1, 'update-department', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11514, 1, 'delete-department', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11515, 1, 'search-department', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11516, 1, 'add-user', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11517, 1, 'update-user', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11518, 1, 'disabled-user', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11519, 1, 'disabled-user-batch', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11520, 1, 'update-user-role', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11521, 1, 'reset-user-password', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11522, 1, 'delete-user', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11523, 1, 'user-operate-log-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11524, 1, 'user-operate-log-detail', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11525, 1, 'user-operate-log-delete', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11526, 1, 'user-login-log-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11527, 1, 'user-login-log-delete', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11528, 1, 'smart-reload-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11529, 1, 'smart-reload-update', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11530, 1, 'smart-reload-result', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11531, 1, 'System', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11532, 1, 'SystemSetting', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11533, 1, 'SysParam', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11534, 1, 'system-params-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11535, 1, 'system-params-add', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11536, 1, 'system-params-update', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11537, 1, 'system-config-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11538, 1, 'SystemResource', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11539, 1, 'resource-main-update', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11540, 1, 'resource-batch-save-points', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11541, 1, 'resource-main-search', '2021-03-22 15:48:48', '2021-03-22 15:48:48');
INSERT INTO `sys_role_resource` VALUES (11553, 34, 'SystemSetting', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11554, 34, 'SysParam', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11555, 34, 'SystemResource', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11556, 34, 'system-params-search', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11557, 34, 'system-params-add', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11558, 34, 'system-params-update', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11559, 34, 'system-config-search', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11560, 34, 'resource-main-update', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11561, 34, 'resource-main-search', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11562, 34, 'System', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11563, 34, 'resource-batch-save-points', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11564, 34, 'UserLog', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11565, 34, 'UserOperateLog', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11566, 34, 'user-operate-log-search', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11567, 34, 'user-operate-log-detail', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11568, 34, 'user-operate-log-delete', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11569, 34, 'UserLoginLog', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11570, 34, 'user-login-log-search', '2021-03-22 16:42:04', '2021-03-22 16:42:04');
INSERT INTO `sys_role_resource` VALUES (11571, 34, 'user-login-log-delete', '2021-03-22 16:42:04', '2021-03-22 16:42:04');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '员工id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 242 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色员工功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (170, 37, 36, '2019-11-08 10:40:16', '2019-11-08 10:40:16');
INSERT INTO `sys_role_user` VALUES (174, 38, 37, '2019-11-08 11:05:39', '2019-11-08 11:05:39');
INSERT INTO `sys_role_user` VALUES (211, 40, 38, '2019-11-15 16:54:54', '2019-11-15 16:54:54');
INSERT INTO `sys_role_user` VALUES (213, 45, 29, '2019-11-16 18:04:04', '2019-11-16 18:04:04');
INSERT INTO `sys_role_user` VALUES (233, 1, 1, '2021-03-16 11:24:02', '2021-03-16 11:24:02');
INSERT INTO `sys_role_user` VALUES (242, 34, 34, '2021-03-22 16:10:51', '2021-03-22 16:10:51');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录帐号',
  `login_pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `actual_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '别名',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `department_id` int(10) UNSIGNED NOT NULL COMMENT '部门id',
  `is_leave` int(11) NOT NULL DEFAULT 0 COMMENT '是否离职1是',
  `is_disabled` int(11) NOT NULL DEFAULT 0 COMMENT '是否被禁用 0否1是',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(10) UNSIGNED NOT NULL COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9c80a23bc64dc2e218b13eec4847e11b', '管理员', '15515515515', '13112312131', '410306199202020020', '1992-02-02', NULL, 1, 0, 0, NULL, 0, '2021-03-22 10:35:52', '2018-05-11 09:38:54', 0);
INSERT INTO `sys_user` VALUES (11, 'role1', '9c80a23bc64dc2e218b13eec4847e11b', '角色测试1', '', '18123245230', '', '1970-01-01', '', 4, 0, 0, NULL, 1, '2019-04-27 09:56:17', '2019-04-25 12:30:22', 0);
INSERT INTO `sys_user` VALUES (12, 'role2', '9c80a23bc64dc2e218b13eec4847e11b', '角色测试2', '', '18121451241', '', NULL, '', 4, 0, 0, NULL, 1, '2019-08-01 10:04:38', '2019-04-25 12:31:11', 0);
INSERT INTO `sys_user` VALUES (13, 'lihaifan', '9c80a23bc64dc2e218b13eec4847e11b', 'lihaifan', '', '18399485774', '', NULL, '', 1, 0, 0, NULL, 1, '2019-04-27 09:56:17', '2019-04-25 13:50:44', 0);
INSERT INTO `sys_user` VALUES (14, 'lipeng', '9c80a23bc64dc2e218b13eec4847e11b', '李鹏1', '', '13937988294', '', NULL, '', 2, 0, 0, NULL, 1, '2019-04-27 09:56:17', '2019-04-25 14:34:47', 0);
INSERT INTO `sys_user` VALUES (15, 'huangwenli', '9c80a23bc64dc2e218b13eec4847e11b', '黄文丽', '', '15515515515', '', NULL, '', 16, 0, 0, NULL, 1, '2019-04-27 09:56:17', '2019-04-26 10:05:05', 0);
INSERT INTO `sys_user` VALUES (16, 'huangwenli1', '9c80a23bc64dc2e218b13eec4847e11b', '黄文丽', '', '15515515515', '', NULL, '', 15, 0, 0, NULL, 1, '2019-04-27 14:04:19', '2019-04-26 10:25:04', 0);
INSERT INTO `sys_user` VALUES (17, 'zhangjiao', '9c80a23bc64dc2e218b13eec4847e11b', '张娇', '阿娇', '15670390391', '410305199102020020', '1991-02-02', '86484@qq.com', 19, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-04-26 14:37:23', 0);
INSERT INTO `sys_user` VALUES (18, 'zhangjiao1', '9c80a23bc64dc2e218b13eec4847e11b', '张娇1', '', '15670390391', '', '2019-04-18', '6666@qq.com', 20, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-04-26 14:45:55', 0);
INSERT INTO `sys_user` VALUES (19, 'zhenxiaocang', '9c80a23bc64dc2e218b13eec4847e11b', '珍小藏', '', '15670390391', '', NULL, '', 19, 0, 1, NULL, 1, '2019-09-09 08:34:35', '2019-04-26 14:46:57', 0);
INSERT INTO `sys_user` VALUES (20, 'matengfei', '9c80a23bc64dc2e218b13eec4847e11b', '马腾飞', '', '15670390393', '', NULL, '', 19, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-04-26 14:47:24', 0);
INSERT INTO `sys_user` VALUES (21, 'ceshi123', '9c80a23bc64dc2e218b13eec4847e11b', '测试人员', '', '18829938477', '', NULL, '', 1, 0, 1, NULL, 13, '2019-04-27 09:56:17', '2019-04-27 09:38:07', 1);
INSERT INTO `sys_user` VALUES (22, 'zhangjingru', '9c80a23bc64dc2e218b13eec4847e11b', '张静如', '', '15600000000', '', NULL, '', 1, 0, 0, NULL, 1, '2019-09-04 09:06:47', '2019-04-28 14:05:03', 0);
INSERT INTO `sys_user` VALUES (23, 'sdfsdfdsfsdfdsfdsf', '9c80a23bc64dc2e218b13eec4847e11b', 'werewr', '', '15698585858', '', NULL, '', 19, 0, 0, NULL, 1, '2019-09-05 16:13:03', '2019-04-28 16:26:27', 0);
INSERT INTO `sys_user` VALUES (25, 'shq2019', '9c80a23bc64dc2e218b13eec4847e11b', 'shq', 'shq', '18798801298', '410281199309024040', '1993-09-02', '', 17, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-05-05 09:13:41', 0);
INSERT INTO `sys_user` VALUES (26, 'zhangjiao666', '9c80a23bc64dc2e218b13eec4847e11b', 'tom我是五个字12', '', '15612345678', '', NULL, '', 18, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-05-05 15:34:10', 0);
INSERT INTO `sys_user` VALUES (28, 'dfsfgds', '9c80a23bc64dc2e218b13eec4847e11b', 'fds', '', '15854127845', '', NULL, '', 22, 0, 1, NULL, 1, '2019-09-06 08:58:40', '2019-05-06 10:36:57', 0);
INSERT INTO `sys_user` VALUES (29, 'abcabc', '9c80a23bc64dc2e218b13eec4847e11b', 'abccba', 'aaabac', '13311112222', '', NULL, '', 17, 0, 0, NULL, 1, '2019-08-05 16:33:57', '2019-07-10 17:00:58', 0);
INSERT INTO `sys_user` VALUES (30, 'gengweigang', '9c80a23bc64dc2e218b13eec4847e11b', '耿为刚', 'geng', '15038588418', '', NULL, '', 17, 0, 0, NULL, 1, '2019-08-08 14:35:51', '2019-08-08 14:35:51', 0);
INSERT INTO `sys_user` VALUES (31, 'gengweigang1', '9c80a23bc64dc2e218b13eec4847e11b', '耿为刚1', '这是别名', '15038588418', '410322193312123232', '1933-12-12', '32@qq.com', 18, 0, 0, NULL, 30, '2019-08-23 09:27:22', '2019-08-23 09:25:50', 0);
INSERT INTO `sys_user` VALUES (32, 'ceshi123', '9c80a23bc64dc2e218b13eec4847e11b', '测试', '测试', '15670702651', '', NULL, '', 17, 0, 0, NULL, 1, '2019-09-04 09:05:48', '2019-09-03 11:48:04', 0);
INSERT INTO `sys_user` VALUES (33, 'ceshi321', '9c80a23bc64dc2e218b13eec4847e11b', '测试', '测试', '15670702651', '', NULL, '', 17, 0, 1, NULL, 1, '2019-09-03 15:51:16', '2019-09-03 11:49:17', 0);
INSERT INTO `sys_user` VALUES (34, 'ceshi123321', '9c80a23bc64dc2e218b13eec4847e11b', '123', '', '15600000000', '', NULL, '', 22, 0, 0, NULL, 1, '2021-03-22 16:11:34', '2019-09-04 09:13:54', 0);
INSERT INTO `sys_user` VALUES (35, 'guoqingfeng', '9c80a23bc64dc2e218b13eec4847e11b', '郭青枫', '', '15670702651', '', NULL, '', 18, 0, 0, NULL, 1, '2019-09-04 15:09:00', '2019-09-04 15:09:00', 0);
INSERT INTO `sys_user` VALUES (36, 'li327263458', '9c80a23bc64dc2e218b13eec4847e11b', 'lipeng', '', '13937988294', '', NULL, '', 17, 0, 0, NULL, 1, '2019-09-09 17:01:39', '2019-09-09 17:01:39', 0);
INSERT INTO `sys_user` VALUES (37, 'test123', '9c80a23bc64dc2e218b13eec4847e11b', 'test', '', '13211110201', '', NULL, '', 18, 0, 1, NULL, 1, '2019-11-14 16:08:08', '2019-11-08 09:32:39', 0);
INSERT INTO `sys_user` VALUES (38, 'tiantian', '9c80a23bc64dc2e218b13eec4847e11b', '天天管理员', '', '13574502368', '', NULL, '', 17, 0, 0, NULL, 1, '2019-11-14 02:08:08', '2019-11-08 11:09:46', 0);
INSERT INTO `sys_user` VALUES (39, 'wang13211111', '9c80a23bc64dc2e218b13eec4847e11b', 'ceshi111', 'dddd', '13244553212', '', NULL, '', 25, 0, 0, NULL, 38, '2019-11-15 17:14:34', '2019-11-15 17:03:04', 0);

-- ----------------------------
-- Table structure for sys_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login_log`;
CREATE TABLE `sys_user_login_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '员工id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `remote_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户ip',
  `remote_port` int(11) NULL DEFAULT NULL COMMENT '用户端口',
  `remote_browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器',
  `remote_os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作系统',
  `login_status` tinyint(4) NOT NULL COMMENT '登录状态 0 失败  1成功',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `customer_id`(`user_id`) USING BTREE,
  INDEX `auditor_id`(`remote_browser`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1805 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户登录日志' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_user_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_operate_log`;
CREATE TABLE `sys_user_operate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作模块',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作内容',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求路径',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求方法',
  `param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求参数',
  `result` tinyint(4) NULL DEFAULT NULL COMMENT '请求结果 0失败 1成功',
  `fail_reason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '失败原因',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 636 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;


-- 导出  表 smart-admin-dev.t_reload_item 结构
DROP TABLE IF EXISTS `t_reload_item`;
CREATE TABLE IF NOT EXISTS `t_reload_item` (
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项名称',
  `args` varchar(255) DEFAULT NULL COMMENT '参数 可选',
  `identification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '运行标识',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  smart-admin-dev.t_reload_item 的数据：~0 rows (大约)
DELETE FROM `t_reload_item`;
/*!40000 ALTER TABLE `t_reload_item` DISABLE KEYS */;
INSERT INTO `t_reload_item` (`tag`, `args`, `identification`, `update_time`, `create_time`) VALUES
	('system_config', '234', 'xxxx', '2019-11-14 16:46:21', '2019-04-18 11:48:27');
/*!40000 ALTER TABLE `t_reload_item` ENABLE KEYS */;

-- 导出  表 smart-admin-dev.t_reload_result 结构
DROP TABLE IF EXISTS `t_reload_result`;
CREATE TABLE IF NOT EXISTS `t_reload_result` (
  `tag` varchar(255) NOT NULL,
  `identification` varchar(255) NOT NULL COMMENT '运行标识',
  `args` varchar(255) DEFAULT NULL,
  `result` tinyint unsigned NOT NULL COMMENT '是否成功 ',
  `exception` text,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  smart-admin-dev.t_reload_result 的数据：~127 rows (大约)
DELETE FROM `t_reload_result`;
/*!40000 ALTER TABLE `t_reload_result` DISABLE KEYS */;
INSERT INTO `t_reload_result` (`tag`, `identification`, `args`, `result`, `exception`, `create_time`) VALUES
	('system_config', '23', '', 1, NULL, '2019-09-07 17:26:04'),
	('system_config', '23', '', 1, NULL, '2019-09-07 17:28:16'),
	('system_config', '23', '', 1, NULL, '2019-09-07 17:35:39'),
	('system_config', '23', '', 1, NULL, '2019-09-07 17:42:58'),
	('system_config', '23', '', 1, NULL, '2019-09-09 08:30:13'),
	('system_config', '23', '', 1, NULL, '2019-09-11 10:38:19'),
	('system_config', '23', '', 1, NULL, '2019-09-11 10:42:46'),
	('system_config', '23', '', 1, NULL, '2019-09-11 10:49:27'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:09:10'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:10:06'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:18:17'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:41:18'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:45:41'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:46:37'),
	('system_config', '23', '', 1, NULL, '2019-09-11 11:50:35'),
	('system_config', '23', '', 1, NULL, '2019-09-11 14:55:00'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:26:19'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:35:51'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:36:19'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:36:53'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:37:58'),
	('system_config', '23', '', 1, NULL, '2019-09-11 15:41:37'),
	('system_config', '23', '', 1, NULL, '2019-09-16 10:12:29'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:14:08'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:18:24'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:23:07'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:24:17'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:30:17'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:31:40'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:32:34'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:52:31'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:55:10'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:55:47'),
	('system_config', '23', '', 1, NULL, '2019-09-20 17:58:49'),
	('system_config', '23', '', 1, NULL, '2019-09-21 10:53:47'),
	('system_config', '23', '', 1, NULL, '2019-09-22 18:24:21'),
	('system_config', '23', '', 1, NULL, '2019-09-24 09:04:42'),
	('system_config', '23', '', 1, NULL, '2019-10-15 11:06:12'),
	('system_config', '23', '', 1, NULL, '2019-10-15 11:22:10'),
	('system_config', '23', '', 1, NULL, '2019-10-15 16:42:16'),
	('system_config', '23', '', 1, NULL, '2019-10-19 15:18:54'),
	('system_config', '23', '', 1, NULL, '2019-10-19 16:50:10'),
	('system_config', '23', '', 1, NULL, '2019-10-21 15:52:25'),
	('system_config', '23', '', 1, NULL, '2019-10-23 10:24:38'),
	('system_config', '23', '', 1, NULL, '2019-10-23 10:28:45'),
	('system_config', '23', '', 1, NULL, '2019-10-23 16:35:45'),
	('system_config', '23', '', 1, NULL, '2019-10-23 16:38:48'),
	('system_config', '23', '', 1, NULL, '2019-10-25 08:52:22'),
	('system_config', '23', '', 1, NULL, '2019-10-28 16:04:30'),
	('system_config', '23', '', 1, NULL, '2019-10-30 19:59:24'),
	('system_config', '23', '', 1, NULL, '2019-10-31 14:29:26'),
	('system_config', '23', '', 1, NULL, '2019-10-31 14:35:38'),
	('system_config', '23', '', 1, NULL, '2019-10-31 15:58:39'),
	('system_config', '23', '', 1, NULL, '2019-10-31 17:34:48'),
	('system_config', '23', '', 1, NULL, '2019-11-01 11:23:26'),
	('system_config', '23', '', 1, NULL, '2019-11-01 14:55:34'),
	('system_config', '23', '', 1, NULL, '2019-11-02 08:49:44'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:40:52'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:42:48'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:47:38'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:50:57'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:51:32'),
	('system_config', '23', '', 1, NULL, '2019-11-02 09:51:48'),
	('system_config', '23', '', 1, NULL, '2019-11-02 15:48:21'),
	('system_config', '23', '', 1, NULL, '2019-11-02 20:48:44'),
	('system_config', '23', '', 1, NULL, '2019-11-02 21:27:50'),
	('system_config', '23', '', 1, NULL, '2019-11-03 22:10:32'),
	('system_config', '23', '', 1, NULL, '2019-11-03 22:10:32'),
	('system_config', '23', '', 1, NULL, '2019-11-04 09:10:24'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-05 10:24:51'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:22:42'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:25:54'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:27:04'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:28:00'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:34:06'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:34:43'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:53:11'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 11:56:05'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 13:52:39'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 15:29:29'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:05:36'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:06:13'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:13:22'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:19:38'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:21:37'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-06 16:22:23'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-08 08:50:08'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-08 13:37:34'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 08:35:08'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 08:54:38'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:00:32'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:01:24'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:24:16'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:26:46'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:43:13'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 09:44:48'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 10:28:30'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-09 11:24:19'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-11 09:40:42'),
	('system_config', '23', '4234234', 1, NULL, '2019-11-13 17:25:42'),
	('system_config', '23343', '2423', 1, NULL, '2019-11-13 20:29:19'),
	('system_config', '23343', '2423', 1, NULL, '2019-11-13 20:29:23'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 11:43:57'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 11:50:18'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 11:51:13'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 11:52:03'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 11:53:02'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 13:49:11'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 13:51:05'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 13:53:53'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 13:55:57'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 16:15:44'),
	('system_config', '23343', '234', 1, NULL, '2019-11-14 16:39:36'),
	('system_config', '23343234234', '234', 1, NULL, '2019-11-14 16:41:05'),
	('system_config', '23343234234', '234', 1, NULL, '2019-11-14 16:41:05'),
	('system_config', 'aaaa', '234', 1, NULL, '2019-11-14 16:41:20'),
	('system_config', 'aaaa', '234', 1, NULL, '2019-11-14 16:41:25'),
	('system_config', '111', '234', 1, NULL, '2019-11-14 16:43:20'),
	('system_config', '111', '234', 1, NULL, '2019-11-14 16:44:13'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-14 16:46:26'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-14 16:46:39'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-14 16:48:47'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-15 14:39:55'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-16 08:47:43'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-16 17:12:10'),
	('system_config', 'xxxx', '234', 1, NULL, '2019-11-16 18:02:57'),
	('system_config', 'xxxx', '234', 1, NULL, '2020-12-14 15:09:53'),
	('system_config', 'xxxx', '234', 1, NULL, '2020-12-14 15:13:33'),
	('system_config', 'xxxx', '234', 1, NULL, '2020-12-14 15:14:09'),
	('system_config', 'xxxx', '234', 1, NULL, '2020-12-14 15:16:23');
/*!40000 ALTER TABLE `t_reload_result` ENABLE KEYS */;