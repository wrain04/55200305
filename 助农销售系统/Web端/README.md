# 助农销售系统

## 系统说明

- 基于 Spring Cloud 2021 、Spring Boot 2.7、 OAuth2 的 RBAC **权限管理系统**
- 基于数据驱动视图的理念封装 element-plus，即使没有 vue 的使用经验也能快速上手
- 提供对常见容器化支持 Docker、Kubernetes、Rancher2 支持
- 提供 lambda 、stream api 、webflux 的生产实践

## 快速开始

### 核心依赖

| 依赖                   | 版本         |
| ---------------------- |------------|
| Spring Boot            | 2.7.3      |
| Spring Cloud           | 2021.0.4   |
| Spring Cloud Alibaba   | 2021.0.4.0 |
| Spring Authorization Server | 0.3.1      |
| Mybatis Plus           | 3.5.2      |
| hutool                 | 5.8.7      |
| Avue                   | 3.1.3     |

### 模块说明

```lua
pig-ui  -- https://gitee.com/log4j/pig-ui

pig
├── pig-auth -- 授权服务提供[3000]
└── pig-common -- 系统公共模块
├── pig-common-bom -- 全局依赖管理控制
├── pig-common-core -- 公共工具类核心包
├── pig-common-datasource -- 动态数据源包
├── pig-common-job -- xxl-job 封装
├── pig-common-log -- 日志服务
├── pig-common-mybatis -- mybatis 扩展封装
├── pig-common-seata -- 分布式事务
├── pig-common-security -- 安全工具类
├── pig-common-swagger -- 接口文档
└── pig-common-feign -- feign 扩展封装
├── pig-register -- Nacos Server[8848]
├── pig-gateway -- Spring Cloud Gateway网关[9999]
└── pig-upms -- 通用用户权限管理模块
└── pig-upms-api -- 通用用户权限管理系统公共api模块
└── pig-upms-biz -- 通用用户权限管理系统业务处理模块[4000]
└── pig-visual
└── pig-monitor -- 服务监控 [5001]
├── pig-codegen -- 图形化代码生成 [5002]
├── pig-sentinel-dashboard -- 流量高可用 [5003]
└── pig-xxl-job-admin -- 分布式定时任务管理台 [5004]
```

### Docker 运行

```
cd pig && mvn clean install && docker-compose up -d

cd pig-ui && npm install -g cnpm --registry=https://registry.npm.taobao.org

cnpm install && cnpm run build:docker && cd docker && docker-compose up -d
```
