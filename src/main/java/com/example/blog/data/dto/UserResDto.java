package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResDto {
    private Long id;
    private String email;
    private String username;

    @Builder
    public UserResDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
