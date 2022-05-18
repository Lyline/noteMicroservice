FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/noteMicroservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} noteMicroservice-0.0.1.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","noteMicroservice-0.0.1.jar"]
EXPOSE 8082