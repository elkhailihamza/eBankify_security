# Build stage using Maven
FROM maven:3.9.9 AS build

WORKDIR /opt/app
COPY ./ /opt/app
RUN mvn clean install

# Run stage using OpenJDK
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /opt/app/target/ebankify_security-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
