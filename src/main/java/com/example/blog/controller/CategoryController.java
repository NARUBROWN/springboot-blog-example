package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.CategoryReqDto;
import com.example.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
