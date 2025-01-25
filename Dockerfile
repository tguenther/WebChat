# Use Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
# Set the working directory in the container
WORKDIR /home/app
# Copy the necessary files to the container
COPY src /home/app/src
COPY pom.xml /home/app
# Build the app
RUN mvn clean package

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim
# Set the working directory in the container
WORKDIR /app

# Copy the Google Cloud credentials file to the container
COPY client-key.json /app/client-key.json
# THIS IS BAD!  Docker doesn't like this.  Run Docker with the
# environmental variable passed on the command line instead.
# Set the environment variable
# ENV GOOGLE_APPLICATION_CREDENTIALS=/app/credentials.json

# Copy the packaged jar file into the container
COPY --from=build /home/app/target/webchat-0.0.1-SNAPSHOT.jar app.jar
# Make port 8080 available to the world outside this container
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]