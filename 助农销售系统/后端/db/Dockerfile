FROM mysql/mysql-server:5.7

MAINTAINER lengleng(wangiegie@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#VOLUME ["/root/db","/db"]
#COPY mysql_config.sh /usr/local/
COPY ./product.sql /docker-entrypoint-initdb.d

COPY ./product_codegen.sql /docker-entrypoint-initdb.d

COPY ./product_config.sql /docker-entrypoint-initdb.d

COPY ./product_job.sql /docker-entrypoint-initdb.d

COPY ./product_order.sql /docker-entrypoint-initdb.d

COPY ./product_goods.sql /docker-entrypoint-initdb.d

COPY ./product_information.sql /docker-entrypoint-initdb.d