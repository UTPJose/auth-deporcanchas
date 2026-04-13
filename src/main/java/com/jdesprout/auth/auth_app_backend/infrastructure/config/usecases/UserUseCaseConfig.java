package com.jdesprout.auth.auth_app_backend.infrastructure.config.usecases;

import com.jdesprout.auth.auth_app_backend.application.usecase.user.*;
import com.jdesprout.auth.auth_app_backend.domain.port.security.PasswordEncoderPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository
    ) {
        return new CreateUserUseCase(
                userRepository,
                roleRepository
        );
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository
    ) {
        return new DeleteUserUseCase(
                userRepository
        );
    }

    @Bean
    public GetAllUserByRoleUseCase getAllUserByRoleUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository
    ) {
        return new GetAllUserByRoleUseCase(
                userRepository
        );
    }

    @Bean
    public GetAllUserUseCase getAllUserUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository
    ) {
        return new GetAllUserUseCase(
                userRepository
        );
    }

    @Bean
    public GetUserByEmailUseCase getUserByEmailUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository
    ) {
        return new GetUserByEmailUseCase(
                userRepository
        );
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository
    ) {
        return new GetUserByIdUseCase(
                userRepository
        );
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            @Qualifier("SecondRepository") UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoderPort
    ) {
        return new UpdateUserUseCase(
                userRepository,
                passwordEncoderPort
        );
    }

}
