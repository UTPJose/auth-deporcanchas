package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.usecase.user.CreateUserUseCase;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;

public class RegisterUserUseCase {

    private final CreateUserUseCase createUser;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterUserUseCase(CreateUserUseCase createUser, PasswordEncoderPort passwordEncoderPort) {
        this.createUser = createUser;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    public User execute(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        return createUser.execute(user);
    }

}
