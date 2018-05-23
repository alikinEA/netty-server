FROM frolvlad/alpine-oraclejdk8:slim
ADD /target/netty-server-jar-with-dependencies.jar netty-server.jar
ENV JAVA_OPTS="-Xmx1g -Xms1g -server -XX:+AggressiveOpts"
ENTRYPOINT exec java $JAVA_OPTS -jar /netty-server.jar