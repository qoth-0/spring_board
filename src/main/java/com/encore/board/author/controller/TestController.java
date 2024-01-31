package com.encore.board.author.controller;

import com.encore.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j // 로그 라이브러리(logback) 사용을 위한 어노테이션 - lombok에 포함
public class TestController {

////    slf4j 어노테이션을 사용하지 않고, 직접 라이브러리 import하여 로거 생성 가능
//    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);
    @GetMapping("/log/test1")
    public String test1() {
        log.debug("디버그 로그입니다.");
        log.info("인포 로그입니다.");
        log.error("에러 로그입니다.");
        return "ok";
    }

    @Autowired
    private AuthorService authorService;

    @GetMapping("/exception/test1/{id}")
    public String exceptionTest1(@PathVariable Long id) {
        authorService.findById(id);
        return "ok";
    }

    @GetMapping("/userinfo/test")
    public String userInfoTest(HttpServletRequest request) {
//        로그인 유저정보 얻는 방식
//        방법 1 : session에 attribute를 통해 접근
        String email1 = request.getSession().getAttribute("email").toString();
        System.out.println("email1 " + email1);
//        방법 2 : session에서 Authentication객체를 접근
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String email2 = securityContext.getAuthentication().getName();
        System.out.println("email2 " + email2);
//        방법 3 : securityContextHolder에서 Authentication객체를 접근 - 주로 사용(1, 2번은 토큰 사용 시 세션x)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email3 = authentication.getName();
        System.out.println("email3 " + email3);
        return null;
    }
}
