package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResDto {
    private Long id;
    private String content;

    @Builder
    public CommentResDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
