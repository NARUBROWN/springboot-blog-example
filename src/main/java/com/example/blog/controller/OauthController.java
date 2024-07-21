package com.example.blog.controller;

import com.example.blog.data.dto.TokenResDto;
import com.example.blog.data.dto.UserInfoResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class OauthController {
    @Value("${client_id}")
    private String clientId;

    @GetMapping("/kakao")
    ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        log.info("kakao login code: " + code);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", "http://localhost:8080/oauth/kakao");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<TokenResDto> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                TokenResDto.class
        );

        TokenResDto tokenResDto = response.getBody();

        return ResponseEntity.status(HttpStatus.OK).body(tokenResDto.getAccess_token());
    }

    @GetMapping("/kakaoGetUserInfo")
    ResponseEntity<?> kakaoGetUserInfo(@RequestParam String AccessCode) {
            RestTemplate rt = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer "+ AccessCode);
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<UserInfoResDto> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                UserInfoResDto.class
        );

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }
}
