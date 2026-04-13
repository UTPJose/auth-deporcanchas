package com.jdesprout.auth.auth_app_backend.domain.port.security;

public interface PasswordEncoderPort {
    String encode(String rawPassword);
}
