# Use the official OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build.gradle, settings.gradle, and gradlew files to the container
COPY build.gradle settings.gradle gradlew ./

# Copy the gradle wrapper folder
COPY gradle ./gradle

# Grant execute permissions to the gradlew script
RUN chmod +x gradlew

# Install dependencies and cache them
RUN ./gradlew build --no-daemon || return 0

# Copy the source code
COPY src ./src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Pass build-time arguments
ARG POSTGRES_DB
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD
ARG JWT_SECRET
ARG GOOGLE_RECAPTCHA_SECRET
ARG ENABLE_CAPTCHA_VERIFICATION
ARG GMAIL_LOGIN
ARG GMAIL_PASSWORD

# Set runtime environment variables
ENV POSTGRES_DB=${POSTGRES_DB}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
ENV GOOGLE_RECAPTCHA_SECRET=${GOOGLE_RECAPTCHA_SECRET}
ENV ENABLE_CAPTCHA_VERIFICATION=${ENABLE_CAPTCHA_VERIFICATION}
ENV GMAIL_LOGIN=${GMAIL_LOGIN}
ENV GMAIL_PASSWORD=${GMAIL_PASSWORD}

# Debug: List files in the build/libs directory
RUN ls build/libs

# Expose the default Spring Boot port
EXPOSE 8080

# Set the entry point to run the Spring Boot application
CMD ["java", "-jar", "build/libs/bugtracker_backend-0.0.1-SNAPSHOT.jar"]
