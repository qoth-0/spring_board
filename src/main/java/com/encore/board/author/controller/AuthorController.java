package com.encore.board.author.controller;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorUpdateReqDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

//    회원 생성
    @GetMapping("/create")
    public String createScreen() {
        return "author/author-create";
    }
    @PostMapping("/create")
    public String authorCreate(AuthorSaveReqDto authorSaveReqDto) {
        authorService.authorCreate(authorSaveReqDto);
        return "redirect:/author/list";
    }

//    회원 목록 조회
    @GetMapping("/list")
    public String authorList(Model model) {
        model.addAttribute("authorList", authorService.authorList());
        return "/author/author-list";
    }

//    회원 상세 조회
    @GetMapping("/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.authorDetail(id));
        return "/author/author-detail";
    }

//    회원 수정
    @PostMapping("/update/{id}")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateReqDto authorUpdateReqDto) {
        authorService.authorUpdate(id, authorUpdateReqDto);
        return "redirect:/author/detail/" + id;
    }

//    회원 삭제
    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id) {
        authorService.authorDelete(id);
        return "redirect:/author/list";
    }

//    엔티티 순환참조 이슈 테스트
//    연관관계가 있는 Author엔티티를 json으로 직렬화를 하게 될 경우
//    순환참조 이슈 발생하므로, dto 사용 필요
    @GetMapping("/{id}/circle/entity")
    @ResponseBody
    public Author circleIssueTest1(@PathVariable Long id) {
        return authorService.findById(id); // author객체 반환
    }

    @GetMapping("/{id}/circle/dto") // dto 사용
    @ResponseBody
    public AuthorDetailResDto circleIssueTest2(@PathVariable Long id) {
        return authorService.authorDetail(id); // dto 반환
    }
}
