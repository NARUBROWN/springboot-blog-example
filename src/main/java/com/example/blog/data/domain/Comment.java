package com.example.blog.data.domain;

import com.example.blog.data.dto.CommentReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies;

    @Builder
    public Comment(String content, Post post) {
        this.content = content;
        this.post = post;
    }

    public void updateByDto(CommentReqDto commentReqDto) {
        this.content = commentReqDto.getContent();
    }

    public void addParent(Comment parent) {
        this.parent = parent;
    }
}
