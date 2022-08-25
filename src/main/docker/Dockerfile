
FROM java:openjdk-8-alpine
ARG JAR_FILE
ARG BUILD_VERSION
VOLUME /tmp


# ADD loan-0.0.6-SNAPSHOT.jar app.jar
ADD ${JAR_FILE}-${BUILD_VERSION}.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]


