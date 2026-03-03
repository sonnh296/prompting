package com.promptcourse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;

    // Explicit constructor for the 5-argument case used in AuthService
    public AuthResponse(String accessToken, String refreshToken, String tokenType, Long userId, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.userId = userId;
        this.username = username;
    }
}
