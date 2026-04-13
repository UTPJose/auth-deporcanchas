package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.usecase.user.CreateUserUseCase;
import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserUseCaseTest {

    private CreateUserUseCase createUserUseCase;
    private PasswordEncoderPort passwordEncoder;

    private RegisterUserUseCase registerUserUseCase;

    User user;

    @BeforeEach
    void setUp() {
        createUserUseCase = mock(CreateUserUseCase.class);
        passwordEncoder = mock(PasswordEncoderPort.class);

        registerUserUseCase =
                new RegisterUserUseCase(createUserUseCase, passwordEncoder);

        user = UserTestBuilder.builder().build();
    }

    @Test
    void encode_password_and_delegate_user_creation() {
        String password = user.getPassword();
        String encodedPassword = "encoded-password";

        when(passwordEncoder.encode(password))
                .thenReturn(encodedPassword);

        when(createUserUseCase.execute(user))
                .thenReturn(user);

        User result = registerUserUseCase.execute(user);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(encodedPassword, user.getPassword()),
                () -> assertEquals(user, result)
        );
    }

}