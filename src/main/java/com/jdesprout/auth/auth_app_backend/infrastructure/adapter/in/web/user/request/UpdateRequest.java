package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.request;

public record UpdateRequest(
        String email,
        String name,
        String password,
        String phoneNumber,
        boolean enabled
) {
}
