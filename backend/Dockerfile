FROM maven:3.6.3-jdk-8 AS build
COPY . /usr/src/app/
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8u242-jdk
COPY --from=build /usr/src/app/target/esportbetting-0.0.1-SNAPSHOT.jar /usr/app/esportbetting-0.0.1-SNAPSHOT.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/usr/app/esportbetting-0.0.1-SNAPSHOT.jar"]
