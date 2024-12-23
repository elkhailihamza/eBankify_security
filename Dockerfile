# Build stage using Maven
FROM maven:3.9.9 AS build

WORKDIR /opt/app
COPY ./ /opt/app

# Accept build arguments for database connection details
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

RUN mvn clean install

# Run stage using OpenJDK
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /opt/app/target/ebankify_security-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8083
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "-Xmx1024M", "-Dserver.port=${PORT}", "app.jar"]
