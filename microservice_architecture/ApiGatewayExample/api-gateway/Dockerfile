FROM amazoncorretto:21-alpine3.21-jdk

WORKDIR /app

COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar

ENTRYPOINT [ "java", "-jar", "api-gateway.jar" ]