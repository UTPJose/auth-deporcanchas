package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.exception.BadCredentialsException;
import com.jdesprout.auth.auth_app_backend.application.exception.UserNotFoundException;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.RefreshTokenResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.port.security.TokenGeneratorPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;

public class RefreshTokenUseCase {

    private final TokenGeneratorPort tokenGeneratorPort;
    private final UserRepositoryPort userRepositoryPort;

    public RefreshTokenUseCase(
            TokenGeneratorPort tokenGeneratorPort,
            UserRepositoryPort userRepositoryPort
    ) {
        this.tokenGeneratorPort = tokenGeneratorPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    public RefreshTokenResult execute(String refreshToken) throws BadCredentialsException {

        if (!tokenGeneratorPort.isRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid Refresh Token Type");
        }

        String userId = tokenGeneratorPort.getUserId(refreshToken);

        User user = userRepositoryPort.findById(userId).orElseThrow(
                UserNotFoundException::new
        );

        String newAccessToken = tokenGeneratorPort.generateAccessToken(user);
        String newRefreshToken = tokenGeneratorPort.generateRefreshToken(user);

        return new RefreshTokenResult(
                newAccessToken,
                newRefreshToken,
                tokenGeneratorPort.getAccessTtlSeconds(),
                user
        );
    }
}