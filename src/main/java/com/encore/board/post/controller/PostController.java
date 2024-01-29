package com.encore.board.post.controller;


import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public String postCreate(Model model, PostSaveReqDto postSaveReqDto) {
        try {
            postService.postCreate(postSaveReqDto);
            return "redirect:/post/list";
        }catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/post/post-create";
        }
    }


    //    게시물 목록 조회
//    @GetMapping("/list")
//    public String postList(Model model) {
//        model.addAttribute("postList", postService.postList());
//        return "/post/post-list";
//    }

////    Page json형태 데이터 확인
//    @GetMapping("/json/list")
//    @ResponseBody
////    localhost:8080/post/json/list?size=xx&page=xx&sort=xx,desc
//    public Page<PostListResDto> postList(Pageable pageable) {
//        Page<PostListResDto> postListResDtos = postService.findAllJson(pageable);
//        return postListResDtos;
//    }

//    페이지네이션 처리
    @GetMapping("/list")
//    localhost:8080/post/json/list?size=xx&page=xx&sort=xx,desc
    public String postList(Model model, @PageableDefault(size=5, sort = "createdTime",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListResDto> postListResDtos = postService.findByAppointment(pageable);
        model.addAttribute("postList", postListResDtos);
        return "post/post-list";
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
