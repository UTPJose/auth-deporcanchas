package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request;

public record LoginRequest(
        String email,
        String password
) {
}
