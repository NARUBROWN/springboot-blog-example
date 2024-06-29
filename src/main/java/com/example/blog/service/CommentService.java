package com.example.blog.service;

import com.example.blog.data.domain.Comment;
import com.example.blog.data.domain.Post;
import com.example.blog.data.dto.CommentReqDto;
import com.example.blog.data.dto.CommentResDto;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public boolean create(CommentReqDto commentReqDto, Long post_id, Long parent_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Comment newComment = Comment.builder()
                .content(commentReqDto.getContent())
                .post(post)
                .build();
        if (parent_id != null) {
            Comment parent = commentRepository.findById(parent_id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
            newComment.addParent(parent);
        }
        try {
            commentRepository.save(newComment);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("댓글을 등록하는데 실패했습니다.");
        }
    }

    public CommentResDto read(Long comment_id) {
        Comment foundComment = commentRepository.findById(comment_id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        return CommentResDto.builder()
                .id(foundComment.getId())
                .content(foundComment.getContent())
                .build();
    }

    public CommentResDto update(CommentReqDto commentReqDto, Long comment_id) {
        Comment foundComment = commentRepository.findById(comment_id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        foundComment.updateByDto(commentReqDto);
        try {
            commentRepository.save(foundComment);
            return CommentResDto.builder()
                    .id(foundComment.getId())
                    .content(foundComment.getContent()).build();
        } catch (Exception e) {
            throw new RuntimeException("댓글을 등록하는데 실패했습니다.");
        }
    }

    public boolean delete(Long comment_id) {
        try {
            commentRepository.deleteById(comment_id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("댓글을 삭제하는데 실패했습니다.");
        }
    }
}
