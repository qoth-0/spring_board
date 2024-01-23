package com.encore.board.author.dto;

import com.encore.board.author.domain.Role;
import lombok.Data;

@Data
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String role;
}




