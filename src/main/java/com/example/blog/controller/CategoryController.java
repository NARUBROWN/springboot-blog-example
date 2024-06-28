package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.CategoryPostReqDto;
import com.example.blog.data.dto.CategoryReqDto;
import com.example.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public CommonResDto<Void> create(@RequestBody CategoryReqDto categoryReqDto) {
        if (categoryService.create(categoryReqDto)) {
            return new CommonResDto<>("카테고리를 생성하는데 성공했습니다.", null);
        }
        return new CommonResDto<>("카테고리를 생성하는데 실패했습니다.", null);
    }

    // 카테고리에 속한 게시글을 불러오기
    @GetMapping("/category-posts/{category_id}")
    public CommonResDto<CategoryPostReqDto> getCategoryPosts(@PathVariable Long category_id) {
        CategoryPostReqDto categoryPostReqDto = categoryService.getCategoryPost(category_id);
        return new CommonResDto<>(
                category_id + "번 카테고리의 게시글 목록을 불러왔습니다.",
                categoryPostReqDto);
    }
}
