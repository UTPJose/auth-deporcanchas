package com.jdesprout.auth.auth_app_backend.application.usecase.auth.query;

public record LoginQuery(
        String email,
        String password
) {
}
