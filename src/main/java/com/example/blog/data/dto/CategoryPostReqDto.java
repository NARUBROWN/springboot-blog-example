package com.example.blog.data.dto;

import java.util.List;

public record CategoryPostReqDto(
        String categoryName,
        List<PostListResDto> postListResDtoList
) {

}
