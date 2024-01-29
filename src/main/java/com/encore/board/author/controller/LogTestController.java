package com.encore.board.author.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j // 로그 라이브러리(logback) 사용을 위한 어노테이션 - lombok에 포함
public class LogTestController {

////    slf4j 어노테이션을 사용하지 않고, 직접 라이브러리 import하여 로거 생성 가능
//    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);
    @GetMapping("/log/test1")
    public String test1() {
        log.debug("디버그 로그입니다.");
        log.info("인포 로그입니다.");
        log.error("에러 로그입니다.");
        return "ok";
    }
}
