package com.jdesprout.auth.auth_app_backend.application.usecase.auth;

import com.jdesprout.auth.auth_app_backend.application.exception.UserDisableException;
import com.jdesprout.auth.auth_app_backend.application.exception.UserNotFoundException;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.query.LoginQuery;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.LoginResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticatedUser;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticationPort;
import com.jdesprout.auth.auth_app_backend.domain.port.security.TokenGeneratorPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;

public class LoginUseCase {

    private final AuthenticationPort authenticationPort;
    private final UserRepositoryPort userRepositoryPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    public LoginUseCase(
            AuthenticationPort authenticationPort,
            UserRepositoryPort userRepositoryPort,
            TokenGeneratorPort tokenGeneratorPort
    ) {
        this.authenticationPort = authenticationPort;
        this.userRepositoryPort = userRepositoryPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    public LoginResult execute(LoginQuery query) {

        AuthenticatedUser authUser = authenticationPort.authenticate(
                        query.email(),
                        query.password()
        );

        User user = userRepositoryPort.findById(authUser.userId())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isEnable()) {
            throw new UserDisableException();
        }

        String acccesToken = tokenGeneratorPort.generateAccessToken(user);
        String refreshToken = tokenGeneratorPort.generateRefreshToken(user);

        return new LoginResult(
                acccesToken,
                refreshToken,
                tokenGeneratorPort.getAccessTtlSeconds(),
                user
        );
    }
}
