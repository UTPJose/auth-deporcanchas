package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseTest {

    private FakeUserRepository userRepository;
    private PasswordEncoderPort passwordEncoder;

    private UpdateUserUseCase updateUserUseCase;

    User user;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        passwordEncoder = mock(PasswordEncoderPort.class);

        updateUserUseCase = new UpdateUserUseCase(
                userRepository,
                passwordEncoder
        );

        user = UserTestBuilder.builder().build();

        userRepository.add(user);
    }

    @Test
    void update_user_name() {
        User updateName = UserTestBuilder.builder()
                .name("nuevo nombre")
                .build();

        User result = updateUserUseCase.execute(updateName, user.getId());

        assertEquals(updateName.getName(), result.getName());
    }

    @Test
    void update_user_email() {
        User updateEmail = UserTestBuilder.builder()
                .email(new Email("nuevo@email.com"))
                .build();

        User result = updateUserUseCase.execute(updateEmail, user.getId());

        assertEquals(updateEmail.getEmail().value(), result.getEmail().value());
    }



    @Test
    void encode_password_when_password_is_updated() {
        String rawPassword = user.getPassword();
        String encodedPassword = "encoded-password";

        User updatePassword = UserTestBuilder.builder()
                .password(rawPassword)
                .build();

        when(passwordEncoder.encode(rawPassword))
                .thenReturn(encodedPassword);

        User result = updateUserUseCase.execute(updatePassword, user.getId());

        assertEquals(encodedPassword, result.getPassword());

        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void update_user_enable_status() {

        User updateEnable = UserTestBuilder.builder()
                .enable(false)
                .build();

        User result = updateUserUseCase.execute(updateEnable, user.getId());

        assertFalse(result.isEnable());
    }

    @Test
    void do_not_change_fields_when_values_are_null() {
        String originalName = user.getName();
        String originalEmail = user.getEmail().value();
        String originalPassword = user.getPassword();

        when(passwordEncoder.encode(originalPassword))
                .thenReturn(originalPassword);

        User updateData = UserTestBuilder.builder().build();

        User result = updateUserUseCase.execute(updateData, user.getId());

        assertEquals(originalName, result.getName());
        assertEquals(originalEmail, result.getEmail().value());
        assertEquals(originalPassword, result.getPassword());

        verify(passwordEncoder).encode(originalPassword);
    }

    @Test
    void throw_exception_when_user_not_found() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> updateUserUseCase.execute(user, "invalid-id")
        );
    }

}