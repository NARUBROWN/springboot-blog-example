package com.example.blog.repository;

import com.example.blog.data.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost_Id(Long post_id);
    List<Comment> findAllByPost_IdAndParentIsNull(Long post_id);
}
