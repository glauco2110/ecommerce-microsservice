FROM maven:3.9.8-eclipse-temurin-21 as build
WORKDIR /
COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /
RUN ls -R /
COPY --from=build ./infrastructure/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]