FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]