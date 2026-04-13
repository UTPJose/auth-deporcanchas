package com.jdesprout.auth.auth_app_backend.infrastructure.config;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepositoryPort roleRepository) {
        return args -> {
            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                roleRepository.save(new Role("1", "ROLE_ADMIN"));
            }

            if (!roleRepository.existsByName("ROLE_USER")) {
                roleRepository.save(new Role("2", "ROLE_USER"));
            }
        };
    }

}
