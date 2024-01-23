package com.encore.board.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdTime;

    public PostDetailResDto(Long id, String title, String contents, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdTime = createdTime;
    }
}
