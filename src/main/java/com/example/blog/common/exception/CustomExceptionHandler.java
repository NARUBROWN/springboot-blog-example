package com.example.blog.common.exception;

import com.example.blog.common.data.dto.CommonResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResDto<Void>> handlerCustomException(CustomException e) {
        return ResponseEntity.status(e.getExceptionCode().getHttpStatus()).body(
                new CommonResDto<>(e.getExceptionCode().getMessage(), null));
    }

}
