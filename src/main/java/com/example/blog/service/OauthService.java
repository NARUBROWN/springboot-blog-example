package com.example.blog.service;

import com.example.blog.data.dto.UserInfoResDto;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final UserService userService;
    private final UserRepository userRepository;
    public void register(UserInfoResDto userInfoResDto) {

    }
}
