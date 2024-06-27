package com.example.blog.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CategoryResDto {
    private Long id;
    private String name;

    @Builder
    public CategoryResDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
