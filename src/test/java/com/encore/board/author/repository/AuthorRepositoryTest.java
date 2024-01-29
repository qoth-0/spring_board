package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

// DataJpaTest : 매 테스트가 종료되면 자동으로 DB 원상복구
// 모든 스프링 빈을 생성하지 않는 DB테스트 특화 어노테이션 - repository 테스트 시 주로 사용
@DataJpaTest
// replace = AutoConfigureTestDatabase.Replace.ANY : H2DB(spring내장 인메모리 DB)가 기본 설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// SpringBootTest : 자동 rollback기능은 지원하지 않음, 별도로 rollback코드 또는 어노테이션 필요
// 실제 스프링 실행과 동일하게 스프링 빈 생성 및 주입 - 속도 느림
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test") // application-test.yml 파일을 찾아 설정값 세팅 (test폴더 내 yml파일 생성해야함)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository; // 테스트 대상

    @Test
    public void authorSaveTest() {
//      데이터 저장 확인 : 객체 만들고 save -> db조회 -> 만든 객체와 비교
//        give-when-then 패턴
//        준비(prepare, given)
        Author author = Author.builder()
                .email("Test5@a.com")
                .name("Test5")
                .password("Test5")
                .role(Role.ADMIN)
                .build();

//        실행(excute, when)
        authorRepository.save(author);
        Author authorDB = authorRepository.findByEmail("Test5@a.com").orElse(null); // DB에서 찾아오기

//        검증(then)
//        Assertions클래스의 기능을 통해 오류의 원인파악, null처리, 가시적으로 성공/실패 여부 확인
        Assertions.assertEquals(author.getEmail(), authorDB.getEmail());
    }

}
