package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.exception.BadCredentialsException;
import com.jdesprout.auth.auth_app_backend.application.exception.UserNotFoundException;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.RefreshTokenResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.port.security.TokenGeneratorPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RefreshTokenUseCaseTest {

    private TokenGeneratorPort tokenGenerator;
    private FakeUserRepository userRepository;

    private RefreshTokenUseCase refreshTokenUseCase;

    User user;

    @BeforeEach
    void setUp() {
        tokenGenerator = mock(TokenGeneratorPort.class);
        userRepository = new FakeUserRepository();

        refreshTokenUseCase = new RefreshTokenUseCase(
                tokenGenerator,
                userRepository
        );

        user = UserTestBuilder.builder().build();
    }

    @Test
    void generate_new_tokens_when_refresh_token_is_valid() throws BadCredentialsException {

        String refreshToken = "valid-refresh-token";

        userRepository.add(user);

        when(tokenGenerator.isRefreshToken(refreshToken))
                .thenReturn(true);

        when(tokenGenerator.getUserId(refreshToken))
                .thenReturn(user.getId());

        when(tokenGenerator.generateAccessToken(user))
                .thenReturn("new-access-token");

        when(tokenGenerator.generateRefreshToken(user))
                .thenReturn("new-refresh-token");

        when(tokenGenerator.getAccessTtlSeconds())
                .thenReturn(3600L);

        RefreshTokenResult result =
                refreshTokenUseCase.execute(refreshToken);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("new-access-token", result.accessToken()),
                () -> assertEquals("new-refresh-token", result.refreshToken()),
                () -> assertEquals(3600L, result.expiresIn()),
                () -> assertEquals(user, result.user())
        );
    }

    @Test
    void throw_exception_when_token_is_not_refresh_type() {
        String refreshToken = "invalid-token";

        when(tokenGenerator.isRefreshToken(refreshToken))
                .thenReturn(false);

        assertThrows(
                BadCredentialsException.class,
                () -> refreshTokenUseCase.execute(refreshToken)
        );
    }

    @Test
    void throw_exception_when_user_not_found() {

        String refreshToken = "valid-refresh-token";

        when(tokenGenerator.isRefreshToken(refreshToken))
                .thenReturn(true);

        assertThrows(
                UserNotFoundException.class,
                () -> refreshTokenUseCase.execute(refreshToken)
        );
    }

}