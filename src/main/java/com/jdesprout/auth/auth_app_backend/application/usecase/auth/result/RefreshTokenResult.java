package com.jdesprout.auth.auth_app_backend.application.usecase.auth.result;

import com.jdesprout.auth.auth_app_backend.domain.model.User;

public record RefreshTokenResult(
        String accessToken,
        String refreshToken,
        long expiresIn,
        User user
) {
}
