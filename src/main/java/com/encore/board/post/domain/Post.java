package com.encore.board.post.domain;

import com.encore.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 3000, nullable = false)
    private String contents;


//    관계성 표시 - post객체 입장에서는 한 사람이 여러개 글을 쓸 수 있으므로 n:1
    @ManyToOne(fetch = FetchType.LAZY)
//    author_id는 DB의 컬럼명, 별다른 옵션 없을 시 author의 pk에 fk가 설정
    @JoinColumn(name = "author_id")
//    @JoinColumn(nullable=false, name="author_email", referenceColumnName="email") // email에 fk 설정(email이 unique한 경우 가능)
    private Author author;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public Post(String title, String contents, Author author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
//        author객체의  posts를 초기화시켜준 후
//        this.author.getPosts().add(this);
    }

    public void postUpdate(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}

