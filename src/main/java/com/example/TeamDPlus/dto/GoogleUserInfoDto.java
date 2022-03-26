package com.example.TeamDPlus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleUserInfoDto {
    private String id;
    private String name;
    private String profile_img;
    private String email;
    private String username;
}
