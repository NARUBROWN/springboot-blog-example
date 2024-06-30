package com.example.blog.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ExceptionCode exceptionCode;
}
