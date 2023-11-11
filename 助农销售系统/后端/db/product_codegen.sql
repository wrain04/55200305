/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.37-log : Database - product_codegen
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`product_codegen` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `product_codegen`;

/*Table structure for table `gen_datasource_conf` */

DROP TABLE IF EXISTS `gen_datasource_conf`;

CREATE TABLE `gen_datasource_conf` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '数据源名称',
  `url` varchar(512) DEFAULT NULL COMMENT 'jdbc-url',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据源表';

/*Data for the table `gen_datasource_conf` */

insert  into `gen_datasource_conf`(`id`,`name`,`url`,`username`,`password`,`del_flag`,`create_time`,`create_by`,`update_time`,`update_by`) values (1585294089405423617,'product_goods','jdbc:mysql://product-mysql:3306/product_goods?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true','root','0cb0TEWS8NiOcarhY8I7+BCylJk8JjmI','0','2022-10-26 23:35:39','admin','2022-10-26 23:35:39','admin'),(1614524064255827969,'product_product','jdbc:mysql://product-mysql:3306/product?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true','root','gvsHHK3b/QQRqTE3eHr22I8wSfcP73D1','0','2023-01-15 15:25:08','admin','2023-01-15 15:25:08','admin'),(1637381616383922177,'product_information','jdbc:mysql://product-mysql:3306/product_information?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true','root','2pNf/BWVHUYTnmX5ZTXA4HaaRJK3X3aX','0','2023-03-19 17:12:53','admin','2023-03-19 17:12:53','admin');

/*Table structure for table `gen_form_conf` */

DROP TABLE IF EXISTS `gen_form_conf`;

CREATE TABLE `gen_form_conf` (
  `id` bigint(20) NOT NULL,
  `table_name` varchar(64) DEFAULT NULL COMMENT '表名',
  `form_info` json NOT NULL COMMENT '表单信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `table_name` (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单配置';

/*Data for the table `gen_form_conf` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
