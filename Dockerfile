# Stage 1: Build stage
FROM maven:3.8.6-jdk-17-slim AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/ebankify_security-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
