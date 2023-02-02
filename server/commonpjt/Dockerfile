FROM openjdk:8-jre
COPY server/commonpjt/build/libs/*.jar server/commonpjt/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]