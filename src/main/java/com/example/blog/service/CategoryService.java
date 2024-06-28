package com.example.blog.service;

import com.example.blog.data.domain.Category;
import com.example.blog.data.dto.*;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

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

    public CategoryPostReqDto getCategoryPost(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 없습니다."));
        List<PostListResDto> postReqDtoList = postRepository.findAllByCategory_Id(categoryId).stream()
                .map(PostListResDto::from).toList();
        return new CategoryPostReqDto(
                foundCategory.getName(), postReqDtoList
        );
    }
}
