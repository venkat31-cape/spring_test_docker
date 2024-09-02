# Maven build container

FROM maven:3.8.5-openjdk-11 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

#pull base image

FROM openjdk:21-jdk-slim

#maintainer
MAINTAINER venkatesh.s@capestart.com
#expose port 8080
EXPOSE 8080

#default command
CMD java -jar /data/venkat-0.0.1-SNAPSHOT.jar

#copy hello world to docker image from builder image

COPY --from=maven_build /tmp/target/venkat-0.0.1-SNAPSHOT.jar /data/venkat-0.0.1-SNAPSHOT.jar