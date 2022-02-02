FROM openjdk:11.0-slim

LABEL MAINTENER="projet2"

RUN apt-get update && \
    apt-get upgrade && \
    apt-get install git && \
    apt-get install maven && \
    apt-get install bash

RUN git clone https://github.com/MaximeDzN/myResto-spring.git

WORKDIR myResto-spring

RUN mvn clean package

RUN cp target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]
