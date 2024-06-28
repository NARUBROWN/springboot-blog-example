package com.example.blog.common.data.dto;

public record CommonResDto<T> (
        String msg,
        T result
) {
}
