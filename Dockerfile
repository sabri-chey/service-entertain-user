FROM openjdk:17-alpine

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

WORKDIR /app

COPY  app/target/*.jar /app/spring-boot-service.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar /app/spring-boot-service.jar