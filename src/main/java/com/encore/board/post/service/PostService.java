package com.encore.board.post.service;

import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void postCreate(PostSaveReqDto postSaveReqDto) {
        Post post = new Post(postSaveReqDto.getTitle(), postSaveReqDto.getContents());
        postRepository.save(post);

    }
    public List<PostListResDto> postList() {
        List<Post> posts = postRepository.findAll();
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for(Post post : posts) {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
            postListResDtos.add(postListResDto);
        }
        return postListResDtos;
    }

    public Post findById(Long id) throws EntityNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public PostDetailResDto postDetail(Long id) throws EntityNotFoundException{
        Post post = this.findById(id);

        PostDetailResDto postDetailResDto = new PostDetailResDto(
                post.getId(), post.getTitle(), post.getContents(), post.getCreatedTime()
        );
        return postDetailResDto;
    }


    public void postUpdate(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = this.findById(id);
        post.postUpdate(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void postDelete(Long id) {
        Post post = this.findById(id);
        postRepository.delete(post);
    }


}
