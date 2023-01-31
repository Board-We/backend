FROM openjdk:17-oracle
ARG JAR_FILE=build/libs/board-we-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} board-we-backend.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/board-we-backend.jar"]
