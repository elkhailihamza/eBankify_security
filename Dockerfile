# Build stage using Maven
FROM maven:3.9.9 AS build

WORKDIR /opt/app
COPY ./ /opt/app
RUN mvn clean install -DskipTests

# Run stage using OpenJDK
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /opt/app/target/ebankify_security-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8083
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "-Xmx1024M", "-Dserver.port=${PORT}", "app.jar"]
