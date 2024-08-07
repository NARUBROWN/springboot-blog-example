package com.example.blog.service;

import com.example.blog.data.domain.User;
import com.example.blog.data.dto.UserReqDto;
import com.example.blog.data.dto.UserResDto;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public UserResDto read(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("찾는 유저가 없습니다."));
        return UserResDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public UserResDto update(Long user_id, UserReqDto userReqDto) {
        User foundUser = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("찾는 유저가 없습니다."));
        foundUser.updateUserByDto(userReqDto);
        try {
            userRepository.save(foundUser);
            return UserResDto.builder()
                    .id(foundUser.getId())
                    .email(foundUser.getEmail())
                    .username(foundUser.getUsername()).build();
        } catch (Exception e) {
            throw new RuntimeException("엔티티를 변경하던 중 문제가 발생했습니다.");
        }
    }

    @Transactional
    public boolean delete(Long user_id) {
        try {
            userRepository.deleteById(user_id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean checkUsernameAvailability(String username) {
       Optional<User> user =  userRepository.findByUsername(username);
       return user.isEmpty();
    }
}
