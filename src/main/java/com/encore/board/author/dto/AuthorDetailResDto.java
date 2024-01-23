package com.encore.board.author.dto;

import com.encore.board.author.domain.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorDetailResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdTime;
    private String role;

    public AuthorDetailResDto(Long id, String name, String email, String password, LocalDateTime createdTime, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
        this.role = role;
    }
}
