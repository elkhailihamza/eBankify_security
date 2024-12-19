# Build stage using Maven 3.9.9 and Java 17
FROM maven:3.9.9 AS build

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /opt/app

COPY ./ /opt/app

RUN mvn clean install -DskipTests

# Run stage with OpenJDK 17
FROM openjdk:17-jdk-alpine

COPY --from=build /opt/app/target/*.jar ebankify.jar

ENV PORT 8083
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "-Xmx1024M", "-Dserver.port=${PORT}", "app.jar"]