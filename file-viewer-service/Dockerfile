#Get Java 8
FROM openjdk:8-jdk-alpine

# Build the project
#FROM maven:3.6.0-jdk-8-slim AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests

ARG JAR_FILE=target/file-viewer-service-2.0.0.RELEASE.jar

# cd /opt/app
#WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]