FROM shipilev/openjdk-shenandoah:11
ADD /target/netty-server-jar-with-dependencies.jar netty-server.jar
ENV JAVA_OPTS="-Xmx2g -Xms2g -server -XX:+UseShenandoahGC -Xlog:gc"
ENTRYPOINT exec java $JAVA_OPTS -jar /netty-server.jar