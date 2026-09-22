#!/bin/bash

./api-gateway/gradlew -p api-gateway bootJar
./eureka-client/gradlew -p eureka-client bootJar
./eureka-server/gradlew -p eureka-server bootJar
docker build -t eureka-client:322 ./eureka-client
docker build -t api-gateway:322 ./api-gateway
docker build -t eureka-server:322 ./eureka-server
docker compose up