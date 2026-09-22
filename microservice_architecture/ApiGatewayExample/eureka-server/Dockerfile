FROM amazoncorretto:21-alpine3.21-jdk

RUN apk add curl

WORKDIR /app

COPY build/libs/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

ENTRYPOINT [ "java", "-jar", "eureka-server.jar" ]