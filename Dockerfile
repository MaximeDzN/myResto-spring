FROM adoptopenjdk/openjdk11:jdk-11.0.9_11-alpine-slim

LABEL MAINTENER="Allan et Brice"

RUN apk update && \
    apk upgrade && \
    apk add git && \
    apk add maven && \
    apk add bash

RUN git clone https://github.com/brikema/GestionGlasses.git

WORKDIR GestionGlasses

RUN mvn clean package

RUN cp target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]