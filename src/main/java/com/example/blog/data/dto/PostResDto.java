package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private List<CommentResDto> comment;

    @Builder
    public PostResDto(Long id, String title, String content, String username, List<CommentResDto> commentResDtoList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.comment = commentResDtoList;
    }
}
