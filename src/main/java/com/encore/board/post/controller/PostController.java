package com.encore.board.post.controller;


import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    게시물 작성
    @GetMapping("/create")
    public String createScreen() {
        return "post/post-create";
    }
    @PostMapping("/create")
    public String postCreate(PostSaveReqDto postSaveReqDto) {
        postService.postCreate(postSaveReqDto);
        return "redirect:/post/list";
    }


    //    게시물 목록 조회
    @GetMapping("/list")
    public String postList(Model model) {
        model.addAttribute("postList", postService.postList());
        return "/post/post-list";
    }

    //    게시물 상세 조회
    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.postDetail(id));
        return "/post/post-detail";
    }

    //    게시물 수정
    @PostMapping("/update/{id}")
    public String postUpdate(@PathVariable Long id, PostUpdateReqDto postUpdateReqDto) {
        postService.postUpdate(id, postUpdateReqDto);
        return "redirect:/post/detail/" + id;
    }

    //    게시물 삭제
    @GetMapping("/delete/{id}")
    public String postDelete(@PathVariable Long id) {
        postService.postDelete(id);
        return "redirect:/post/list";
    }
}
