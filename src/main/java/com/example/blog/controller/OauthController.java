package com.example.blog.controller;

import com.example.blog.data.dto.TokenResDto;
import com.example.blog.data.dto.UserInfoResDto;
import com.example.blog.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/oauth")
@Slf4j
@RequiredArgsConstructor
public class OauthController {
    @Value("${client_id}")
    private String clientId;
    private final WebClient webClient;
    private final OauthService oauthService;

    @GetMapping("/kakao")
    ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        log.info("kakao login code: " + code);

        String requestUri = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/token")
                        .queryParam("grant_type", "authorization_code").queryParam("client_id", clientId)
                        .queryParam("redirect_uri", "http://localhost:8080/oauth/kakao").queryParam("code", code)
                        .toUriString();

        TokenResDto tokenResDto =
                webClient.post()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(TokenResDto.class)
                .block();

        UserInfoResDto response = webClient.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + tokenResDto.getAccess_token())
                .retrieve()
                .bodyToMono(UserInfoResDto.class)
                .block();

        return ResponseEntity.status(HttpStatus.OK).body(oauthService.register(response));
    }
}
