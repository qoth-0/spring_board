package com.encore.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security 설정을 커스터마이징 하기 위함
// pre : 사전, post: 사후 - 사전, 사후에 인증/권한 검사 어노테이션 사용
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //    WebSecurityConfigurerAdapter를 상속하는 방식은 deprecated(지원종료)되었다.
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
//        security 설정 후 뜨는 로그인 화면 관련 커스텀
        return httpSecurity
                .csrf().disable() // csrf보안 공격에 대한 설정은 하지 않겠다 라는 의미
                .authorizeRequests() // 보안인증처리 대상 url, 미대상 url(회원가입, 로그인, home) 설정
                // 인증 미적용 url
                    .antMatchers("/", "/author/create", "/author/login-page")
                        .permitAll()
                // 그 외 요청은 모두 인증 필요
                    .anyRequest().authenticated()
                .and()
////                세션이 스프링 시큐리티의 기본 - 세션 방식을 사용하지 않으면 아래 내용 설정필요
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page")
//                스프링 내장메서드를 사용하기 위해 /doLogin url 사용
                    .loginProcessingUrl("/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("pw")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                spring security의 doLogout기능 그대로 사용
                    .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
