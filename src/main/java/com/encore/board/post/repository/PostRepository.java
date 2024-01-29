package com.encore.board.post.repository;

import com.encore.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByCreatedTimeDesc(); // 생성시간으로 내림차순 정렬

//    pageable객체 : pageNumber(page=1), page당 개수(size=10), 정렬(sort=createdTime, desc) 설정
//    Page 객체 : List<Post> + 해당 page의 각종 정보
    Page<Post> findAll(Pageable pageable);

//    예약여부에 따라 조회 (Y or null)
    Page<Post> findByAppointment(String appointment, Pageable pageable);

//    JPQL문
    @Query("select p from Post p left join p.author")
    //    select p.* from post p left join author a on p.author_id = a.id; - author객체 안들어 가있음 -> 그러면 join을 왜해?
    //     -> author 객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    List<Post> findAllJoin();
    @Query("select p from Post p left join fetch p.author")
    //    select p.*, a.* from post p left join author a on p.author_id = a.id; - author객체 들어가있음
    List<Post> findAllFetchJoin();
}


