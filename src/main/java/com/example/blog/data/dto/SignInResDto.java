package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignInResDto {
    private String token;
    @Builder
    public SignInResDto(String token) {
        this.token = token;
    }
}
