# Build stage using Maven 3.9.9 and Java 17
FROM maven:3.9.9 AS build

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz

WORKDIR /opt/app

COPY ./ /opt/app

RUN mvn clean install -DskipTests

# Run stage with OpenJDK 17
FROM openjdk:17-jdk-alpine

COPY --from=build /opt/app/target/*.jar app.jar

ENV PORT 8083
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "-Xmx1024M", "-Dserver.port=${PORT}", "app.jar"]