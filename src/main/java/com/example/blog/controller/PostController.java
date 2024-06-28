package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.PostReqDto;
import com.example.blog.data.dto.PostResDto;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 등록
    @PostMapping
    public CommonResDto<Void> create(
            @RequestBody PostReqDto postReqDto,
            @RequestParam("username") String username,
            @RequestParam("category_id") Long category_id) {
        if (postService.create(postReqDto, username, category_id)) {
            return new CommonResDto<>("게시글이 등록되었습니다.", null);
        }
        return new CommonResDto<>("게시글을 등록하는데 실패했습니다.", null);
    }

    // 게시글 읽기
    @GetMapping("/{post_id}")
    public CommonResDto<PostResDto> getPosts(@PathVariable Long post_id) {
        return new CommonResDto<>(post_id + "번 게시글을 조회했습니다.",
                postService.read(post_id));
    }

    // 게시글 수정
    @PutMapping
    public CommonResDto<PostResDto> update(
            @RequestBody PostReqDto postReqDto,
            @RequestParam("post_id") Long post_id) {
        return new CommonResDto<>(post_id + "번 게시글이 수정되었습니다.",
                postService.update(post_id, postReqDto));
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public CommonResDto<Void> delete(@PathVariable Long post_id) {
        if (postService.delete(post_id)) {
            return new CommonResDto<>("게시글을 삭제하는데 성공했습니다.", null);
        }
        return new CommonResDto<>("게시글을 삭제하는데 실패했습니다.", null);
    }
}
