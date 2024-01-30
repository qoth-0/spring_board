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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.DateFormatter;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void postCreate(PostSaveReqDto postSaveReqDto, String email) throws IllegalArgumentException{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
        Author author = authorRepository.findByEmail(email).orElse(null);
//        예약여부(o), 시간설정(o)일때
        LocalDateTime appointTime = null;
        String appointment = null; // Y가 아니면 다 null로 들어감
        if(postSaveReqDto.getAppointment().equals("Y") && !postSaveReqDto.getAppointmentTime().isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            예약시간을 dateTimeFormatter의 형식으로 맞춰줌
            appointTime = LocalDateTime.parse(postSaveReqDto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(appointTime.isBefore(now)) {
                throw new IllegalArgumentException("예약시간이 현재시간보다 빠릅니다.");
            }
            appointment = "Y";
        }
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(author)
                .appointment(appointment)
                .appointmentTime(appointTime)
                .build();

        postRepository.save(post);
    }
//    public List<PostListResDto> postList() {
//        List<Post> posts = postRepository.findAllFetchJoin();
//        List<PostListResDto> postListResDtos = new ArrayList<>();
//        for(Post post : posts) {
//            PostListResDto postListResDto = new PostListResDto();
//            postListResDto.setId(post.getId());
//            postListResDto.setTitle(post.getTitle());
////            게시글의 작성자가 null이면 익명유저로 set, 존재하면 해당 email을 set
//            postListResDto.setAuthor_email(post.getAuthor() == null ? "익명유저" : post.getAuthor().getEmail());
//            postListResDtos.add(postListResDto);
//        }
//        return postListResDtos;
//    }

////    모든 POST Paging처리 및 조회
//    public Page<PostListResDto> findAllPaging(Pageable pageable) {
//        Page<Post> posts = postRepository.findAll(pageable);
//        Page<PostListResDto> postListResDtos = posts.map(
//                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명유저" : p.getAuthor().getEmail()));
//        return postListResDtos;
//    }

    public Page<PostListResDto> findByAppointment(Pageable pageable) {
        Page<Post> posts = postRepository.findByAppointment(null, pageable);
        Page<PostListResDto> postListResDtos = posts.map(
                p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명유저" : p.getAuthor().getEmail()));
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
