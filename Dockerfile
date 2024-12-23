# Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the project files (replace with your actual build artifact location)
COPY target/ebankify-security.jar /app/ebankify-security.jar

# Expose the port the app runs on
EXPOSE 8081

# Set the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/ebankify-security.jar"]
