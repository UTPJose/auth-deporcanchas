package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;

import java.time.Instant;

public class UpdateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoderPort;

    public UpdateUserUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoderPort
    ) {
        this.userRepository = userRepository;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public User execute(User user, String userId) {
        User existingUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con esa id no existe"));
        if(user.getName() != null) existingUser.setName(user.getName());
        if(user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if(user.getImage() != null) existingUser.setImage(user.getImage());
        if(user.getPassword() != null) existingUser.setPassword(passwordEncoderPort.encode(user.getPassword()));
        existingUser.setEnable(user.isEnable());
        existingUser.setUpdateAt(Instant.now());
        return userRepository.save(existingUser);
    }

}
