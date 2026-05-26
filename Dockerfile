# 1. 빌드 스테이지 (호스트 아키텍처인 amd64 네이티브로 빌드하여 속도 극대화 및 exec format error 차단)
FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jdk AS builder
WORKDIR /build

# 빌드에 필요한 최소 파일들 복사
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./
COPY src src

# gradlew 실행 권한 부여 및 bootJar 빌드
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

# 2. 실행 스테이지 (최종 배포용 아키텍처 환경 - 멀티 플랫폼 지원)
FROM eclipse-temurin:17-jdk
WORKDIR /app

# 빌드 스테이지에서 최종 추출된 .jar 파일만 가볍게 복사
COPY --from=builder /build/build/libs/*.jar app.jar

# 운영 환경 프로파일 지정 및 실행 포트 설정
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]