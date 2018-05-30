FROM shipilev/openjdk-shenandoah
ADD /target/netty-server-jar-with-dependencies.jar netty-server.jar
ENV JAVA_OPTS="-Xmx2g -Xms2g -server -XX:+AggressiveOpts"
ENTRYPOINT exec java $JAVA_OPTS -jar /netty-server.jar