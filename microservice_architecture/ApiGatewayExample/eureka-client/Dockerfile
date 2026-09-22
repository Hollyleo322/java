FROM amazoncorretto:21-alpine3.21-jdk

WORKDIR /app

COPY build/libs/eureka-client-0.0.1-SNAPSHOT.jar eureka-client.jar

ENTRYPOINT [ "java", "-jar", "eureka-client.jar" ]