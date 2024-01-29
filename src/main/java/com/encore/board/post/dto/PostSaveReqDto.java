package com.encore.board.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSaveReqDto {
    private String title;
    private String contents;
    private String email;
    private String appointment;
    private String appointmentTime;
}
