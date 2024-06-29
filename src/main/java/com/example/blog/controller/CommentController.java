package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.CommentReqDto;
import com.example.blog.data.dto.CommentResDto;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public CommonResDto<Void> createComment(
            @RequestBody CommentReqDto commentReqDto,
            @RequestParam("post_id") Long post_id,
            @RequestParam(name = "parent_id", required = false) Long parent_id
    ) {
        if (commentService.create(commentReqDto, post_id, parent_id)) {
            return new CommonResDto<>("댓글 등록이 완료되었습니다.", null);
        }
        return new CommonResDto<>("댓글을 등록하는데 실패했습니다.", null);
    }

    // 댓글 단건 조회
    @GetMapping("/{comment_id}")
    CommonResDto<CommentResDto> getComment(@PathVariable Long comment_id) {
        CommentResDto result = commentService.read(comment_id);
        return new CommonResDto<>(comment_id + "번 댓글을 불러오는데 성공했습니다.", result);
    }

    // 댓글 수정
    @PutMapping
    CommonResDto<CommentResDto> updateComment(
            @RequestParam("comment_id") Long comment_id,
            @RequestBody CommentReqDto commentReqDto
    ) {
        CommentResDto result = commentService.update(commentReqDto, comment_id);
        return new CommonResDto<>(comment_id + "번 댓글을 수정했습니다.", result);
    }

    // 댓글 삭제
    @DeleteMapping("/{comment_id}")
    CommonResDto<Void> deleteComment(@PathVariable Long comment_id) {
        if (commentService.delete(comment_id)) {
            return new CommonResDto<>(comment_id + "번 댓글을 삭제했습니다.", null);
        }
        return new CommonResDto<>(comment_id + "번 댓글을 삭제하는데 실패했습니다.", null);
    }
}
