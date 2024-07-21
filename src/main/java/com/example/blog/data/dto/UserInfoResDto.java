package com.example.blog.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResDto {
    private Long id;
    private Properties properties;

    @Getter
    @Setter
    public static class Properties {
        private String nickname;
        private String profileImage;
        private String thumbnailImage;
    }
}
