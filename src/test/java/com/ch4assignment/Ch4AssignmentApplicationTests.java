package com.ch4assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local") // 테스트 실행 시 application-local.properties를 강제 적용합니다.
class Ch4AssignmentApplicationTests {

    static {
        // AWS SDK가 자격 증명이 없어도 에러를 내지 않도록 시스템 프로퍼티에 가짜 키를 심어줍니다.
        System.setProperty("aws.accessKeyId", "mock-access-key");
        System.setProperty("aws.secretAccessKey", "mock-secret-key");
        System.setProperty("aws.region", "ap-northeast-2");
    }

    @Test
    void contextLoads() {
        // 테스트 환경에서 스프링 컨텍스트가 에러 없이 정상적으로 뜨는지 검증합니다.
    }

}