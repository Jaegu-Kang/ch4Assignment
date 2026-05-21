# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon -x test

# 2단계: 실행 스테이지 (경량 JRE 환경)
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar app.ja

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
EXPOSE 8080
