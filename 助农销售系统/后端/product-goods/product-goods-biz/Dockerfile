FROM moxm/java:1.8-full

RUN mkdir -p /product-goods-biz

WORKDIR goods

ARG JAR_FILE=target/product-goods-biz.jar

COPY ${JAR_FILE} app.jar

EXPOSE 17001

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar app.jar $JAVA_OPTS
#CMD java -jar app.jar $JAVA_OPTS