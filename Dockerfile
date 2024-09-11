# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS maven_build

# Copy the entire project directory structure into the build context
COPY pom.xml /tmp/
COPY venkat-api /tmp/venkat-api
COPY venkat-service /tmp/venkat-service
COPY venkat-common /tmp/venkat-common

# Set the working directory and build the project
WORKDIR /tmp/
RUN mvn package -DskipTests -pl venkat-api -am

# Runtime stage
FROM openjdk:21

# Maintainer
LABEL maintainer="venkatesh.s@capestart.com"

# Expose port 8080
EXPOSE 8080

# Copy the JAR file from the Maven build container to the new image
COPY --from=maven_build /tmp/venkat-api/target/venkat-api-0.0.1-SNAPSHOT.jar /data/venkat-api.jar

# Default command to run the JAR file
CMD ["java", "-jar", "/data/venkat-api.jar"]
