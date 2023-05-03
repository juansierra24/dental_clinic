# Use a Java runtime as the base image
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot executable JAR file into the container
COPY build/libs/splitwise-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port that the Spring Boot app listens on
EXPOSE 8080

# Set the command to run the Spring Boot app when the container starts
CMD ["java", "-jar", "app.jar"]