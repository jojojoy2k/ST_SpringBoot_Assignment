FROM openjdk:17

ENV APP_JAR build/libs/*.war

COPY ${APP_JAR} app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar"]

