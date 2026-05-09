package com.jdesprout.auth.auth_app_backend.domain.port.auth;

public record AuthenticatedUser(
        Long userId,
        String email
) {
}
