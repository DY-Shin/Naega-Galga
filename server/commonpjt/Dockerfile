FROM openjdk:8-jre
ARG JAR_FILE=server/commonpjt/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]