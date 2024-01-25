package com.encore.board.author.controller;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// WebMvcTest : Controller계층을 테스트, 모든 스프링빈을 생성하고 주입하지는 않음
@WebMvcTest(AuthorController.class)

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

//    @Test
//    @WithMockUser // 사용하려면 security 의존성 추가 필요
//    void authorDetailTest() throws Exception{
//        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
//        authorDetailResDto.setName("test");
//        authorDetailResDto.setEmail("test");
//        authorDetailResDto.setPassword("test");
//
//        Mockito.when(authorService.authorDetail(1L)).thenReturn(authorDetailResDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", "testname"));
//    }
}
