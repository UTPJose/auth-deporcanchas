package com.jdesprout.auth.auth_app_backend.infrastructure.security.config;

import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import com.jdesprout.auth.auth_app_backend.infrastructure.security.adapter.SpringPasswordEncoderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityAdapterConfig {

    @Bean
    public PasswordEncoderPort passwordEncoderPort(
            PasswordEncoder passwordEncoder
    ) {
        return new SpringPasswordEncoderAdapter(passwordEncoder);
    }

}
