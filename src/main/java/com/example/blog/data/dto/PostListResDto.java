package com.example.blog.data.dto;

import com.example.blog.data.domain.Post;
import lombok.Builder;

@Builder
public record PostListResDto(
        Long postId,
        String title,
        String username
) {
    public static PostListResDto from(Post post) {
        return PostListResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .username(post.getUser().getUsername())
                .build();
    }
}
