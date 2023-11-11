FROM moxm/java:1.8-full

RUN mkdir -p /product-order-biz

WORKDIR order

ARG JAR_FILE=target/product-order-biz.jar

COPY ${JAR_FILE} app.jar

EXPOSE 17002

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar app.jar $JAVA_OPTS
#CMD java -jar app.jar $JAVA_OPTS
