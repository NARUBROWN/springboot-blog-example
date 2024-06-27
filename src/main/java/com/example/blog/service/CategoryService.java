package com.example.blog.service;

import com.example.blog.data.domain.Category;
import com.example.blog.data.dto.CategoryReqDto;
import com.example.blog.data.dto.CategoryResDto;
import com.example.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public boolean create(CategoryReqDto categoryReqDto) {
        Category category = new Category(categoryReqDto.getName());
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("카테고리를 생성하는데 실패했습니다.");
        }

    }

    public CategoryResDto read(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("카테고리를 찾는데 실패했습니다."));
        return CategoryResDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public boolean update(CategoryReqDto categoryReqDto, Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("카테고리를 찾는데 실패했습니다."));
        foundCategory.updateName(categoryReqDto.getName());

        try {
            categoryRepository.save(foundCategory);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("카테고리를 생성하는데 실패했습니다.");
        }
    }

    public boolean delete(Long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("카테고리를 삭제하는데 실패했습니다.");
        }
    }
}
