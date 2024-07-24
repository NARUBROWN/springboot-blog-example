package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserReqDto {
    private String username;
    private String password;
    private String email;

    @Builder
    public UserReqDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
