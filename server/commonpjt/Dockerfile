FROM openjdk:8-jre
COPY ./server/commonpjt/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]