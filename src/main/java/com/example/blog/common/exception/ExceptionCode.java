package com.example.blog.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    // User
    USER_JPA_ERROR(HttpStatus.BAD_REQUEST, "JPA 에러가 발생했습니다."),
    USER_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "찾는 유저가 없습니다.")
    ;

    // Post
    // Comment
    // Category

    private final HttpStatus httpStatus;
    private final String message;
    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
