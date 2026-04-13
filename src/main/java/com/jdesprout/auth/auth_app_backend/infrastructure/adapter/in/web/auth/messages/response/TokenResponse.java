package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        long expiresIn,
        String tokenType,
        UserResponse user
) {

    public static TokenResponse of(String accessToken, String refreshToken, long expiresIn, UserResponse user) {
        return new TokenResponse(accessToken, refreshToken, expiresIn, "Bearer", user);
    }

}
