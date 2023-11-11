# 项目快速部署
1. 拉取项目

   ````sh
   mvn clean spring-javaformat:apply install -T 1C -Dmaven.test.skip=true -Dmaven.compile.fork=true
   mvn clean spring-javaformat:apply install -T 1C -Dmaven.test.skip=true  -Dmaven.compile.fork=true -DarchetypeCatalog=internal
   ````
   
2. docker命令启动服务

````sh
docker-compose build --force-rm --no-cache && docker-compose up -d	#在product目录下执行
````

## 附录
## docker+docker-compose安装

### docker

1. 删除老版本docker

   ````sh
   sudo yum remove docker docker-common container-selinux docker-selinux docker-engine
   ````

2. 更新yum

   ````sh
   yum update -y 
   ````

3. 安装yum-utils

   ````sh
   sudo yum install -y yum-utils
   ````

4. 添加yum源

   ````sh
   sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   ````

5. 更新索引

   ````sh
   sudo yum makecache fast	#Centos7
   sudo yum makecache		#Centos8
   ````

6. 安装docker-ce

   centos8 需要先安装依赖包，centos7可直接跳过下面两步直接安装docker-ce

   + 获取rpm

     ````sh
     wget https://download.docker.com/linux/centos/7/x86_64/edge/Packages/containerd.io-1.2.6-3.3.el7.x86_64.rpm
     ````

   + 安装

     ````sh
     yum -y install containerd.io-1.2.6-3.3.el7.x86_64.rpm
     ````

   ````sh
   sudo yum install -y docker-ce
   ````

7. 启动docker

   ````sh
   sudo systemctl start docker
   ````

8. 验证

   ````sh
   sudo docker info
   ````

9. 阿里云镜像加速器

   到阿里云查看自己的镜像加速器配置


### docker-compose

1. 安装epel源

   ````sh
   yum install -y epel-release
   ````

2. 安装docker-compose

   ````sh
   yum install -y docker-compose 
   ````

### 其他命令

````sh
docker stop $(docker ps -a -q) &&docker  rm $(docker ps -a -q)	#暂停所有容器并删除所有容器
docker image prune
````

````sh
#添加数据库远程访问
docker exec -it product-mysql /bin/bash

mysql -u root -p3485983aaa

ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '3485983aaa';

flush privileges;
exit
exit

````
````sh
mvn spring-javaformat:apply #编译项目问题：Run `spring-javaformat:apply` to fix. 的解决方案
````
````sh
#部署服务器同步git代码,开发勿用
git reset --hard
git pull
````