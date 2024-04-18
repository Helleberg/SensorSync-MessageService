FROM openjdk:17-alpine

EXPOSE 8284

COPY ./target/message-service-*.jar message-service.jar
COPY service-healthcheck.sh service-healthcheck.sh

CMD ["./service-healthcheck.sh", "device-service", "8282", "java", "-jar", "message-service.jar"]