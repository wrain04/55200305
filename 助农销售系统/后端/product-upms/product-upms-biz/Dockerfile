FROM moxm/java:1.8-full

RUN mkdir -p /product-upms-biz

WORKDIR /product-upms-biz

ARG JAR_FILE=target/product-upms-biz.jar

COPY ${JAR_FILE} app.jar

EXPOSE 4000

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar app.jar $JAVA_OPTS
