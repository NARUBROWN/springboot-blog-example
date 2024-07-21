package com.example.blog.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResDto {
    private String access_token;
    private String refresh_token;
}
