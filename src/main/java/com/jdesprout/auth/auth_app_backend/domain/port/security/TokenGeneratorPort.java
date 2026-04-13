package com.jdesprout.auth.auth_app_backend.domain.port.security;

import com.jdesprout.auth.auth_app_backend.domain.model.User;

public interface TokenGeneratorPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean isAccessToken(String token);
    boolean isRefreshToken(String token);
    String getUserId(String token);
    long getAccessTtlSeconds();
}
