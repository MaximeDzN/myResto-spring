FROM openjdk:11.0-slim

LABEL MAINTENER="projet2"

RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install git -y && \
    apt-get install maven -y && \
    apt-get install bash -y

RUN git clone https://github.com/MaximeDzN/myResto-spring.git

WORKDIR myResto-spring

RUN mvn clean package

RUN cp target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]
