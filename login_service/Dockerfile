## Use an official OpenJDK runtime as a base image
#FROM openjdk:17-jdk-slim
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy your application JAR file into the container
#COPY target/login_service-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the application port (update if your app uses a different port)
#EXPOSE 8080
#
## Command to run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]


# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set the working directory in the build container
WORKDIR /app

# Copy the Maven project files into the build container
COPY pom.xml .
COPY src ./src

# Build the application and skip tests to save time
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime container
FROM openjdk:17-jdk-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy the JAR file from the build container
COPY --from=builder /app/target/login_service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
