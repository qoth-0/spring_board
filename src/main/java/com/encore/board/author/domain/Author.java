package com.encore.board.author.domain;

import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.post.domain.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter // 회원목록 조회 - service단에서 getID, Name, Email에 사용
@Entity
@NoArgsConstructor
//    @Builder 어노테이션을 생성자 위에 선언하여 Builder 패턴으로 객체 생성
//    매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅가능
//    단, 해당 생성자의 매개변수로 정의된 변수만 세팅 가능
@Builder // AllArgsConstructor 위에 선언 시 클래스 내 모든 변수 세팅 가능
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

//    author를 조회할 때 post객체가 필요할 시 선언
//    mappedBy에 연관관계의 주인을 명시하고, fk를 관리하는 변수명을 명시
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @Setter // casecade.persist 테스트를 위한 setter
    private List<Post> posts;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // current_timestamp 설정
    private LocalDateTime createdTime;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // update 시 갱신
    private LocalDateTime updatedTime;

    public void authorUpdate(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
