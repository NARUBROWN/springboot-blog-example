package com.example.blog.data.domain;

import com.example.blog.data.dto.PostReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updatePostByDto(PostReqDto postReqDto) {
        this.title = postReqDto.getTitle();
        this.content = postReqDto.getContent();
    }

}
