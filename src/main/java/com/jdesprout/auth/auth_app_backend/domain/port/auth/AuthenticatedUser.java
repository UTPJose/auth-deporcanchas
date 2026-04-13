package com.jdesprout.auth.auth_app_backend.domain.port.auth;

public record AuthenticatedUser(
        String userId,
        String email
) {
}
