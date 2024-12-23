# Run stage using OpenJDK
FROM openjdk:17-jdk-alpine

WORKDIR /app
COPY --from=build /opt/app/target/ebankify_security-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
