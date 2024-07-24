package com.example.blog.controller;

import com.example.blog.common.data.dto.CommonResDto;
import com.example.blog.data.dto.UserReqDto;
import com.example.blog.data.dto.UserResDto;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /*
    * 회원정보 불러오기
    * */
    @GetMapping("/{user_id}")
    public CommonResDto<UserResDto> getUser(@PathVariable Long user_id) {
        return new CommonResDto<>("회원정보를 불러왔습니다.",  userService.read(user_id));
    }

    /*
    * 회원정보 수정
    * */
    @PutMapping
    public CommonResDto<UserResDto> updateUser(
            @RequestParam("user_id") Long user_id,
            @RequestBody UserReqDto userReqDto
    ) {
        return new CommonResDto<>(
                user_id + " 에 대한 회원정보가 수정되었습니다.",
                userService.update(user_id, userReqDto)
        );
    }

    /*
    * 회원 탈퇴
    * */
    @DeleteMapping("/{user_id}")
    public CommonResDto<Void> unregister(@PathVariable Long user_id) {
        if (userService.delete(user_id)) {
            return new CommonResDto<>("계정이 정상적으로 삭제되었습니다.", null);
        }
        return new CommonResDto<>("계정을 삭제하는데 실패했습니다.", null);
    }


    // username 중복체크
    @GetMapping("/check-username-availability")
    public CommonResDto<Void> checkUsernameAvailability(@RequestParam("username") String username) {
        if (userService.checkUsernameAvailability(username)) {
            return new CommonResDto<>("사용가능한 Username입니다.", null);
        }
        return new CommonResDto<>("사용 불가능한 Username입니다.", null);
    }
}
