FROM openjdk:11-jdk-slim

LABEL MAINTENER="projet2"

RUN apt-get update -y && \
    apt-get upgrade -y  && \
    apt-get install -y git && \
    apt-get install -y maven


RUN git clone -b DevOps https://github.com/MaximeDzN/myResto-spring.git

WORKDIR myResto-spring

RUN ls

COPY images/ images/

RUN mvn clean package

RUN cp target/*.jar app.jar

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]