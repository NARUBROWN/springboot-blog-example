package com.example.blog.service;

import com.example.blog.data.domain.User;
import com.example.blog.data.dto.SignInReqDto;
import com.example.blog.data.dto.SignInResDto;
import com.example.blog.data.dto.UserReqDto;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public boolean signUp(UserReqDto userReqDto) {
        User newUser = User.builder()
                .email(userReqDto.getEmail())
                .username(userReqDto.getUsername())
                .password(passwordEncoder.encode(userReqDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        try {
            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SignInResDto signIn(SignInReqDto signInReqDto) {
        User foundUser = userRepository.findByEmail(signInReqDto.getEmail()).orElseThrow(() -> new RuntimeException("Email을 사용하는 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(signInReqDto.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("패스워드가 일치하지 않음");
        }

        String accessToken = jwtProvider.createAccessToken(foundUser.getId(), foundUser.getEmail());
        return SignInResDto.builder()
                .token(accessToken)
                .build();
    }
}
