package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostReqDto {
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
}
