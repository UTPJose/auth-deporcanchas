package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;

public class GetUserByEmailUseCase {

    private final UserRepositoryPort userRepository;

    public GetUserByEmailUseCase(
            UserRepositoryPort userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User whit that email does not exist")
                );
    }
}
