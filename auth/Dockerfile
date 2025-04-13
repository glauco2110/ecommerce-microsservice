FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

COPY target/auth-service.jar auth-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auth-service.jar"]