package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.SignInReqDto;
import com.example.blog.data.dto.SignInResDto;
import com.example.blog.data.dto.UserReqDto;
import com.example.blog.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    /*
     * 회원가입
     * */
    @PostMapping("/sign-up")
    public CommonResDto<Void> singUp(@RequestBody UserReqDto userReqDto) {
        if (signService.signUp(userReqDto)) {
            return new CommonResDto<>("회원가입이 완료되었습니다.", null);
        }
        return new CommonResDto<>("회원가입에 실패했습니다.", null);
    }

    @PostMapping("/sign-in")
    ResponseEntity<CommonResDto<SignInResDto>> signIn(@RequestBody SignInReqDto signInReqDto) {
        SignInResDto signInResDto = signService.signIn(signInReqDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResDto<>("Access Token 입니다.", signInResDto)
        );
    }

}
