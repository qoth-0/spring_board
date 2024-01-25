package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void postCreate(PostSaveReqDto postSaveReqDto) {
        Author author = authorRepository.findByEmail(postSaveReqDto.getEmail()).orElse(null);
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(author)
                .build();

//        더티체킹 테스트
        author.authorUpdate("dirty checking test", "1234");
        postRepository.save(post);
    }
    public List<PostListResDto> postList() {
        List<Post> posts = postRepository.findAllFetchJoin();
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for(Post post : posts) {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
//            게시글의 작성자가 null이면 익명유저로 set, 존재하면 해당 email을 set
            postListResDto.setAuthor_email(post.getAuthor() == null ? "익명유저" : post.getAuthor().getEmail());
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
