package com.ch4assignment.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[ERROR] 잘못된 요청 발생", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("[WARN] 정적 리소스 부재 (ALB 우회 처리): {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 경로의 리소스가 존재하지 않습니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("[ERROR] 서버 내부 오류 발생", e);
        return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
    }

}
