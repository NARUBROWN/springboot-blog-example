package com.example.blog.service;

import com.example.blog.common.exception.CustomException;
import com.example.blog.common.exception.ExceptionCode;
import com.example.blog.data.domain.User;
import com.example.blog.data.dto.TokenResDto;
import com.example.blog.data.dto.UserInfoResDto;
import com.example.blog.data.dto.UserReqDto;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final SignService signService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResDto register(UserInfoResDto userInfoResDto) {
        String userEmail = userInfoResDto.getKakao_account().getEmail();
        String nickname = userInfoResDto.getProperties().getNickname();
        Optional<User> foundUser = userRepository.findByEmail(userEmail);
        if (foundUser.isEmpty()) {
            UserReqDto userReqDto = UserReqDto.builder()
                    .email(userEmail)
                    .username(nickname)
                    .password(RandomStringUtils.randomAlphabetic(15))
                    .build();
            if (signService.signUp(userReqDto)) {
                return TokenResDto.builder().access_token(jwtProvider.createAccessToken(userEmail)).build();
            } else {
                throw new CustomException(ExceptionCode.USER_JPA_ERROR);
            }
        } else {
            return TokenResDto.builder().access_token(jwtProvider.createAccessToken(userEmail)).build();
        }
    }

}
