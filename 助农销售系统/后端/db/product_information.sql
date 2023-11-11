/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.37-log : Database - product_information
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`product_information` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `product_information`;

/*Table structure for table `information` */

DROP TABLE IF EXISTS `information`;

CREATE TABLE `information` (
  `information_id` bigint(20) NOT NULL COMMENT '资讯id',
  `author` varchar(64) NOT NULL COMMENT '作者',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `text` varchar(255) DEFAULT NULL COMMENT '文本',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo',
  `view_num` int(11) DEFAULT NULL COMMENT '浏览量',
  `update_by` varchar(64) NOT NULL COMMENT '修改人',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`information_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资讯表';

/*Data for the table `information` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
