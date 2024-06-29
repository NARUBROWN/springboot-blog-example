package com.example.blog.service;

import com.example.blog.data.domain.Category;
import com.example.blog.data.domain.Comment;
import com.example.blog.data.domain.Post;
import com.example.blog.data.domain.User;
import com.example.blog.data.dto.CommentResDto;
import com.example.blog.data.dto.PostReqDto;
import com.example.blog.data.dto.PostResDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public boolean create(PostReqDto postReqDto, String username, Long category_id) {
        Post newPost = Post.builder()
                .title(postReqDto.getTitle())
                .content(postReqDto.getContent())
                .build();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

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
    public PostResDto read(Long post_id) {
       Post foundPost = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
       // 루트 댓글 조회
       List<Comment> commentList = commentRepository.findAllByPost_IdAndParentIsNull(post_id);
       List<CommentResDto> commentResDtoList = commentList.stream().map(this::convertTo).collect(Collectors.toList());

       return PostResDto.builder()
               .id(foundPost.getId())
               .username(foundPost.getUser().getUsername())
               .title(foundPost.getTitle())
               .content(foundPost.getContent())
               .commentResDtoList(commentResDtoList)
               .build();
    }

    private CommentResDto convertTo(Comment comment) {
        return CommentResDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .replies(comment.getReplies().stream()
                        .map(this::convertTo)
                        .collect(Collectors.toList()))
                .build();
    }

    public PostResDto update(Long post_id, PostReqDto postReqDto) {
        Post foundPost = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        foundPost.updatePostByDto(postReqDto);
        try {
            postRepository.save(foundPost);
            return PostResDto.builder()
                    .id(foundPost.getId())
                    .title(foundPost.getTitle())
                    .content(foundPost.getContent())
                    .username(foundPost.getUser().getUsername())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("게시글을 저장하는데 오류가 발생했습니다.");
        }
    }

    public boolean delete(Long post_id) {
        try {
            postRepository.deleteById(post_id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
