package com.encore.board.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인이 성공했을 때 실행할 로직 정의
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession httpSession = request.getSession(); // 사용자요청(request)에서 세션객체를 꺼냄
//        authentication 객체 안에는 User객체가 들어가 있고, 여기서 getName은 email을 의미
        httpSession.setAttribute("email", authentication.getName()); // authentication에 담긴 email을 꺼내서 세션 객체에 저장
        response.sendRedirect("/"); // 홈화면으로 이동
    }
}
