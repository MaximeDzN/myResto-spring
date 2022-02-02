FROM adoptopenjdk/openjdk11:jdk-11.0.9_11-alpine-slim

LABEL MAINTENER="Allan et Brice"

RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install git -y && \
    apt-get install maven -y && \
    apt-get install bash -y

RUN git clone https://github.com/brikema/GestionGlasses.git

WORKDIR GestionGlasses

RUN mvn clean package

RUN cp target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]
