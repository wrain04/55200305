/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.37-log : Database - product_config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`product_config` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `product_config`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8_bin,
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`,`encrypted_data_key`) values (1,'application-dev.yml','DEFAULT_GROUP','# 加解密根密码\njasypt:\n  encryptor:\n    password: pig #根密码\n    \n# Spring 相关\nspring:\n  cache:\n    type: redis\n  redis:\n    host: product-redis\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: product-sentinel:5003\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"  \n  endpoint:\n    health:\n      show-details: ALWAYS\n\n\n# feign 配置\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 20000\n        readTimeout: 20000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n\n# mybaits-plus配置\nmybatis-plus:\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  global-config:\n    banner: false\n    db-config:\n      id-type: auto\n      table-underline: true\n      logic-delete-value: 1\n      logic-not-delete-value: 0\n  configuration:\n    map-underscore-to-camel-case: true\n\n# spring security 配置\nsecurity:\n  oauth2:\n    # 通用放行URL，服务个性化，请在对应配置文件覆盖\n    ignore:\n      urls:\n        - /v3/api-docs\n        - /actuator/**\n\n# swagger 配置\nswagger:\n  enabled: true\n  title: Product Swagger API\n  gateway: http://${GATEWAY_HOST:product-gateway}:${GATEWAY-PORT:9999}\n  token-url: ${swagger.gateway}/auth/oauth2/token\n  scope: server\n  services:\n    product-upms-biz: admin\n    product-codegen: gen\n    product-goods-biz: goods','e7686060b05a451f16299ec2ffd92c7a','2022-05-08 12:10:37','2022-12-27 19:04:26','nacos','127.0.0.1','','','','','','yaml','',''),(2,'product-auth-dev.yml','DEFAULT_GROUP','# 数据源\nspring:\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/','74f53b71c7799aa754da75662378b93c','2022-05-08 12:10:37','2022-06-04 14:15:35','nacos','127.0.0.1','','','','','','yaml','',''),(3,'product-codegen-dev.yml','DEFAULT_GROUP','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: ENC(27v1agvAug87ANOVnbKdsw==)\n      client-secret: ENC(VbnkopxrwgbFVKp+UxJ2pg==)\n      scope: server\n\n# 数据源配置\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    username: root\n    password: 3485983aaa\n    url: jdbc:mysql://product-mysql:3306/product_codegen?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n','dac78651ed84d152d97899eac3b4859b','2022-05-08 12:10:37','2022-10-22 22:52:00','nacos','127.0.0.1','','','','','','yaml','',''),(4,'product-gateway-dev.yml','DEFAULT_GROUP','spring:\n  cloud:\n    gateway:\n      locator:\n        enabled: true\n      routes:\n        # 认证中心\n        - id: product-auth\n          uri: lb://product-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            # 验证码处理\n            - ValidateCodeGatewayFilter\n            # 前端密码解密\n            - PasswordDecoderFilter\n        #UPMS 模块\n        - id: product-upms-biz\n          uri: lb://product-upms-biz\n          predicates:\n            - Path=/admin/**\n          filters:\n            # 限流配置\n            - name: RequestRateLimiter\n              args:\n                key-resolver: \'#{@remoteAddrKeyResolver}\'\n                redis-rate-limiter.replenishRate: 100\n                redis-rate-limiter.burstCapacity: 200\n        # 代码生成模块\n        - id: product-codegen\n          uri: lb://product-codegen\n          predicates:\n            - Path=/gen/**\n        # 固定路由转发配置 无修改\n        - id: openapi\n          uri: lb://product-gateway\n          predicates:\n            - Path=/v3/api-docs/**\n          filters:\n            - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/$\\{path}/v3/api-docs\n            # 商品模块\n        - id: product-goods-biz\n          uri: lb://product-goods-biz\n          predicates:\n            - Path=/goods/**\n          filters:\n            # 限流配置\n            - name: RequestRateLimiter\n              args:\n                key-resolver: \'#{@remoteAddrKeyResolver}\'\n                redis-rate-limiter.replenishRate: 100\n                redis-rate-limiter.burstCapacity: 200\n        # 订单模块\n        - id: product-order-biz\n          uri: lb://product-order-biz\n          predicates:\n            - Path=/orders/**\n          filters:\n            # 限流配置\n            - name: RequestRateLimiter\n              args:\n                key-resolver: \'#{@remoteAddrKeyResolver}\'\n                redis-rate-limiter.replenishRate: 100\n                redis-rate-limiter.burstCapacity: 200\n\ngateway:\n  encode-key: \'thanks,pig4cloud\'\n  ignore-clients:\n    - test\n    - client\n    - product','991275b306c4f1449008a894dd9366d0','2022-05-08 12:10:37','2022-10-31 15:05:08','nacos','127.0.0.1','','','','','','yaml','',''),(5,'product-monitor-dev.yml','DEFAULT_GROUP','spring:\n  autoconfigure:\n    exclude: com.xyw.product.common.core.config.JacksonConfiguration\n  # 安全配置\n  security:\n    user:\n      name: ENC(8Hk2ILNJM8UTOuW/Xi75qg==)     # product\n      password: ENC(o6cuPFfUevmTbkmBnE67Ow====) # product\n','650bdfa15f60f3faa84dfe6e6878b8cf','2022-05-08 12:10:37','2022-05-08 12:10:37',NULL,'127.0.0.1','','',NULL,NULL,NULL,'yaml',NULL,''),(6,'product-upms-biz-dev.yml','DEFAULT_GROUP','security:\n  oauth2:\n    client:\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\n      scope: server\n\n# 数据源\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    username: root\n    password: 3485983aaa\n    url: jdbc:mysql://product-mysql:3306/product?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\n    hikari:\n      pool-name: knmarket-manage  # 连接池名字\n      minimum-idle: 10 # 最小空闲连接数量\n      idle-timeout: 600000 # 空闲连接存活最大时间，默认600000（10分钟）\n      maximum-pool-size: 20 # 连接池最大连接数，默认是20\n      auto-commit: true # 此属性控制从池返回的连接的默认自动提交行为,默认值：true\n      max-lifetime: 1800000 # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟\n      connection-timeout: 30000 # 数据库连接超时时间,默认30秒，即30000\n# 文件上传相关 支持阿里云、华为云、腾讯、minio\noss:\n  endpoint: https://xyw-product.oss-cn-guangzhou.aliyuncs.com\n  accessKey: LTAI5t6XK7ncHWF5p1AtyPx1\n  secretKey: o7FcjTHN7SXMu26Ok90pH7Po4Tiu67\n  bucket-name: xyw-product','ee511b4f2d4d959f7a4aca71789633a5','2022-05-08 12:10:37','2022-10-31 14:48:11','nacos','127.0.0.1','','','','','','yaml','',''),(13,'product-goods-biz-dev.yml','DEFAULT_GROUP','security:\n  oauth2:\n    client:\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\n      scope: server\n\n# 数据源\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    username: root\n    password: 3485983aaa\n    url: jdbc:mysql://product-mysql:3306/product_goods?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\n    hikari:\n      pool-name: knmarket-manage  # 连接池名字\n      minimum-idle: 10 # 最小空闲连接数量\n      idle-timeout: 600000 # 空闲连接存活最大时间，默认600000（10分钟）\n      maximum-pool-size: 20 # 连接池最大连接数，默认是20\n      auto-commit: true # 此属性控制从池返回的连接的默认自动提交行为,默认值：true\n      max-lifetime: 1800000 # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟\n      connection-timeout: 30000 # 数据库连接超时时间,默认30秒，即30000\n# 文件上传相关 支持阿里云、华为云、腾讯、minio\noss:\n  endpoint: https://xyw-product.oss-cn-guangzhou.aliyuncs.com\n  accessKey: LTAI5t6XK7ncHWF5p1AtyPx1\n  secretKey: o7FcjTHN7SXMu26Ok90pH7Po4Tiu67\n  bucket-name: xyw-product','867e4b9df23461939ebd47bda0519e47','2022-10-27 14:15:30','2022-10-31 14:48:21','nacos','127.0.0.1','','','','','','yaml','',''),(14,'product-order-biz-dev.yml','DEFAULT_GROUP','security:\n  oauth2:\n    client:\n      client-id: ENC(imENTO7M8bLO38LFSIxnzw==)\n      client-secret: ENC(i3cDFhs26sa2Ucrfz2hnQw==)\n      scope: server\n\n# 数据源\nspring:\n  datasource:\n    type: com.zaxxer.hikari.HikariDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    username: root\n    password: 3485983aaa\n    url: jdbc:mysql://product-mysql:3306/product_order?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true\n    hikari:\n      pool-name: knmarket-manage  # 连接池名字\n      minimum-idle: 10 # 最小空闲连接数量\n      idle-timeout: 600000 # 空闲连接存活最大时间，默认600000（10分钟）\n      maximum-pool-size: 20 # 连接池最大连接数，默认是20\n      auto-commit: true # 此属性控制从池返回的连接的默认自动提交行为,默认值：true\n      max-lifetime: 1800000 # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟\n      connection-timeout: 30000 # 数据库连接超时时间,默认30秒，即30000\n# 文件上传相关 支持阿里云、华为云、腾讯、minio\noss:\n  endpoint: https://xyw-product.oss-cn-guangzhou.aliyuncs.com\n  accessKey: LTAI5t6XK7ncHWF5p1AtyPx1\n  secretKey: o7FcjTHN7SXMu26Ok90pH7Po4Tiu67\n  bucket-name: xyw-product','f014dfd3cc5349701d2f1b7b9f5395cd','2023-01-17 00:27:54','2023-02-04 17:07:59','nacos','119.134.174.43','','','','','','yaml','','');

/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

/*Data for the table `config_info_aggr` */

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/*Data for the table `config_info_beta` */

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/*Data for the table `config_info_tag` */

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/*Data for the table `config_tags_relation` */

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/*Data for the table `group_capacity` */

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
  `id` bigint(20) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

/*Data for the table `his_config_info` */

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(255) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permissions` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`username`,`role`) values ('nacos','ROLE_ADMIN');

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

/*Data for the table `tenant_capacity` */

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

/*Data for the table `tenant_info` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
