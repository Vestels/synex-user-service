FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21

COPY --from=build /app/target/synex-user-service.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]