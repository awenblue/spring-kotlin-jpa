# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="awenwhy@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 31000

# The application's jar file
ARG JAR_FILE=build/libs/spring-kotlin-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} spring-kotlin.jar

# Run the jar file
ENTRYPOINT ["java","-jar","spring-kotlin.jar"]