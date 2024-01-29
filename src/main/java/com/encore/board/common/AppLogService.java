package com.encore.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AppLogService {

    // AOP의 대상이 되는 controller, service 등을 정의
    // board밑의 모든 controller패키지에 속한 모든 메서드대해 실행
//    @Pointcut("excution(* com.encore.board..controller..*.*(..)")  // 패키지 방식
    @Pointcut("within(@org.springframework.stereotype.Controller *)") // 어노테이션 방식
    public void controllerPointcut() {

    }
////     방식 1. berfore + after
////        메서드 실행 전 인증, 입력값 검증 등을 수행하는 용도로 사용하는 단계
//    @Before("controllerPointcut()") // 상단의 pointcut을 사용하겠다
//    public void beforeController(JoinPoint joinPoint) {
//        log.info("Before Controller");
//
//        //        사용자의 요청값을 출력하기 위해 httpServletRequest 객체를 꺼내는 로직
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
////        사용자의 요청을 json형태로 조립하기 위한 로직
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode objectNode = objectMapper.createObjectNode();
//        objectNode.put("Method Name", joinPoint.getSignature().getName());
//        objectNode.put("CRUD Name", httpServletRequest.getMethod());
//        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
//        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
//        objectNode.set("user inputs", objectNodeDetail);
//        log.info("user request info" + objectNode);
//
//    }
//
//    @After("controllerPointcut()")
//    public void afterController() {
//        log.info("end controller");
//    }

//    방식 2. around 사용 - 가장 빈번
    @Around("controllerPointcut()")
//    join point란 aop의 대상으로 하는 컨트롤러의 특정 메서드를 의미
    public Object controllerLogger(ProceedingJoinPoint proceedingJoinPoint) {
//        log.info("request method" + proceedingJoinPoint.getSignature().toString());
//        사용자의 요청값을 출력하기 위해 httpServletRequest 객체를 꺼내는 로직
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
//        사용자의 요청을 json형태로 조립하기 위한 로직
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Method Name", proceedingJoinPoint.getSignature().getName());
        objectNode.put("CRUD Name", httpServletRequest.getMethod());
        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
        ObjectNode objectNodeDetail = objectMapper.valueToTree(paramMap);
        objectNode.set("user inputs", objectNodeDetail);
        log.info("user request info" + objectNode);

        try {
//            본래의 컨트롤러 메서드 호출하는 부분
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        } finally { // 이후 작업
            log.info("end controller");
        }
    }
}
