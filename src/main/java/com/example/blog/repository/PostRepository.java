package com.example.blog.repository;

import com.example.blog.data.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory_Id(Long category_id);
    List<Post> findAllByUser_Id(Long user_id);
}
