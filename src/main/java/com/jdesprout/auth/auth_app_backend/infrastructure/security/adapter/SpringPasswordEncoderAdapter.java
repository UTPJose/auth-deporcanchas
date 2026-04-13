package com.jdesprout.auth.auth_app_backend.infrastructure.security.adapter;

import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SpringPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    public SpringPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
