FROM maven:3.9.8-eclipse-temurin-21 AS maven_build

# Copy Maven configuration and source code
COPY pom.xml /tmp/
COPY src /tmp/src/

# Set the working directory and build the project
WORKDIR /tmp/
RUN mvn package

# Pull base image
FROM openjdk:21

# Maintainer
LABEL maintainer="venkatesh.s@capestart.com"

# Expose port 8080
EXPOSE 8080

# Copy the JAR file from the Maven build container to the new image
COPY --from=maven_build /tmp/target/venkat-0.0.1-SNAPSHOT.jar /data/venkat-0.0.1-SNAPSHOT.jar

# Default command to run the JAR file
CMD ["java", "-jar", "/data/venkat-0.0.1-SNAPSHOT.jar"]
