FROM openjdk:11-jdk-alpine

LABEL MAINTENER="projet2"

RUN apk update && \
    apk upgrade && \
    apk add git && \
    apk add maven && \
    apk add bash

RUN git clone https://github.com/MaximeDzN/myResto-spring.git

WORKDIR myResto-spring

RUN mvn clean package

RUN cp target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]