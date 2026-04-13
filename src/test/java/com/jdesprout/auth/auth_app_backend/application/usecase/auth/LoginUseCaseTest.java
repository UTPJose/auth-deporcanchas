package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.exception.UserDisableException;
import com.jdesprout.auth.auth_app_backend.application.exception.UserNotFoundException;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.query.LoginQuery;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.LoginResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticatedUser;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticationPort;
import com.jdesprout.auth.auth_app_backend.domain.port.security.TokenGeneratorPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUseCaseTest {

    private AuthenticationPort authentication;
    private FakeUserRepository userRepository;
    private TokenGeneratorPort tokenGenerator;

    private LoginUseCase loginUseCase;

    LoginQuery query;
    AuthenticatedUser authUser;

    User user;

    @BeforeEach
    void setUp() {
        authentication = mock(AuthenticationPort.class);
        userRepository = new FakeUserRepository();
        tokenGenerator = mock(TokenGeneratorPort.class);
        loginUseCase = new LoginUseCase(
                authentication,
                userRepository,
                tokenGenerator

        );

        query = new LoginQuery("user@test.com", "123456");
        user = UserTestBuilder.builder().build();
        authUser = new AuthenticatedUser(user.getId(), user.getEmail().value());
    }

    @Test
    void login_when_credentials_are_valid_and_user_enabled() {

        userRepository.add(user);

        when(authentication.authenticate(
                query.email(),
                query.password()
        ))
                .thenReturn(authUser);

        when(tokenGenerator.generateAccessToken(user))
                .thenReturn("access-token");

        when(tokenGenerator.generateRefreshToken(user))
                .thenReturn("refresh-token");

        when(tokenGenerator.getAccessTtlSeconds())
                .thenReturn(3600L);

        LoginResult result = loginUseCase.execute(query);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("access-token", result.accessToken()),
                () -> assertEquals("refresh-token", result.refreshToken()),
                () -> assertEquals(3600L, result.accessTtlSeconds()),
                () -> assertEquals(user, result.user())
        );

    }

    @Test
    void throw_exception_when_user_not_found() {

        when(authentication.authenticate(
                query.email(),
                query.password()
        ))
                .thenReturn(authUser);

        userRepository.findById(authUser.userId());

        assertThrows(UserNotFoundException.class,
                () -> loginUseCase.execute(query));
    }

    @Test
    void throw_exception_when_user_is_disabled() {
        userRepository.add(user);

        when(authentication.authenticate(
                query.email(),
                query.password()
        ))
                .thenReturn(authUser);

        user.setEnable(false);

        userRepository.findById(user.getId());

        assertThrows(UserDisableException.class,
                () -> loginUseCase.execute(query));
    }

}