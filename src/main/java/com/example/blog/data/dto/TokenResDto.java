package com.example.blog.data.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResDto {
    private String access_token;
    private String refresh_token;
}
