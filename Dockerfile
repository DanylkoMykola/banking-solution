FROM gradle:8.4-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew clean build -x test
RUN ./gradlew jacocoTestReport

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/banking-solution-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/build/reports/jacoco/test/html /app/jacoco-report
COPY src/main/resources/db/changelog /liquibase/changelog

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
