package com.encore.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

// ControllerAdvice와 ExceptionHandler를 통해 예외처리의 공통화 로직 작성
@ControllerAdvice
@Slf4j
public class CommonException {

//    프로그램 어디서든 controller단에서 예외가 터지면 잡아줌
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandler(EntityNotFoundException e) {
        log.error("Handler EntityNotFoundException : " + e.getMessage());
        return this.errResMessage(HttpStatus.NOT_FOUND, e.getMessage()); // 404
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> illegalArgHandler(IllegalArgumentException e) {
        log.error("Handler IllegalArgumentException : " + e.getMessage());
        return this.errResMessage(HttpStatus.BAD_REQUEST, e.getMessage()); // 400
    }

//    에러 공통화 동적으로 하기 - map형태의 메시지 커스텀
    private ResponseEntity<Map<String, Object>> errResMessage(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("error message", message);
        body.put("status message", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }
}
