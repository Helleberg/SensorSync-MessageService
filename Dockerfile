FROM openjdk:17-alpine

EXPOSE 8284

COPY ./target/message-service-*.jar message-service.jar

RUN apk --no-cache add curl

ENTRYPOINT ["java", "-jar", "message-service.jar"]
