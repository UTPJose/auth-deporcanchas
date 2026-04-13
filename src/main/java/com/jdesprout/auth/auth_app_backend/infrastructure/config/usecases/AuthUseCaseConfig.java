package com.jdesprout.auth.auth_app_backend.infrastructure.config.usecases;

import com.jdesprout.auth.auth_app_backend.application.usecase.auth.LoginUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.RefreshTokenUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.RegisterUserUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.user.CreateUserUseCase;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticationPort;
import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import com.jdesprout.auth.auth_app_backend.domain.port.security.TokenGeneratorPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCaseConfig {

    @Bean
    public LoginUseCase loginUseCase(
            AuthenticationPort authenticationPort,
            TokenGeneratorPort tokenGeneratorPort,
            @Qualifier("SecondRepository") UserRepositoryPort userRepositoryPort
    ) {
        return new LoginUseCase(
                authenticationPort,
                userRepositoryPort,
                tokenGeneratorPort
        );
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            TokenGeneratorPort tokenGeneratorPort,
            @Qualifier("SecondRepository") UserRepositoryPort userRepositoryPort
    ) {
        return new RefreshTokenUseCase(
                tokenGeneratorPort,
                userRepositoryPort
        );
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            CreateUserUseCase createUser,
            PasswordEncoderPort passwordEncoderPort
    ) {
        return new RegisterUserUseCase(
                createUser,
                passwordEncoderPort
        );
    }

}
