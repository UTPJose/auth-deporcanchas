package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.response;

public record UserResponse(
        String email,
        String name,
        String password,
        boolean enable,
        String rolName
) {

    public static UserResponse of(String email, String name, String password, boolean enable, String rolName) {
        return new UserResponse(email, name, password, true, rolName);
    }
}
