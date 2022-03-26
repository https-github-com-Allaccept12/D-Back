package com.example.dplus.dto.fcm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FCMToken {
    private String username;
    private String token;

    @Builder
    public FCMToken(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
