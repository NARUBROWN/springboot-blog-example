package com.example.blog.service;

import com.example.blog.data.domain.Category;
import com.example.blog.data.domain.Post;
import com.example.blog.data.domain.User;
import com.example.blog.data.dto.PostReqDto;
import com.example.blog.data.dto.PostResDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public boolean save(PostReqDto postReqDto) {
        Post newPost = Post.builder()
                .title(postReqDto.getTitle())
                .content(postReqDto.getContent())
                .build();

        User user = userRepository.findById(postReqDto.getUserId()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Category category = categoryRepository.findById(postReqDto.getCategoryId()).orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        newPost.updateUser(user);
        newPost.updateCategory(category);

        try {
            postRepository.save(newPost);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public PostResDto read(Long id) {
       Post foundPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
       return PostResDto.builder()
               .id(foundPost.getId())
               .username(foundPost.getUser().getUsername())
               .title(foundPost.getTitle())
               .content(foundPost.getContent())
               .categoryName(foundPost.getCategory().getName())
               .build();
    }

    public PostResDto update(Long id, PostReqDto postReqDto) {
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        foundPost.updatePostByDto(postReqDto);
        try {
            postRepository.save(foundPost);
            return PostResDto.builder()
                    .id(foundPost.getId())
                    .title(foundPost.getTitle())
                    .content(foundPost.getContent())
                    .username(foundPost.getUser().getUsername())
                    .categoryName(foundPost.getCategory().getName())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("게시글을 저장하는데 오류가 발생했습니다.");
        }
    }

    public boolean delete(Long id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
