package com.example.blog.data.dto;

import com.example.blog.data.domain.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResDto {
    private Long id;
    private String content;
    private List<CommentResDto> replies;

    @Builder
    public CommentResDto(Long id, String content, List<CommentResDto> replies) {
        this.id = id;
        this.content = content;
        this.replies = replies;
    }

    public static CommentResDto from(Comment comment) {
        return CommentResDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}
