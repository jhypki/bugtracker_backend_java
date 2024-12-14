FROM openjdk:17-jdk-alpine
COPY target/bugtracker_backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
