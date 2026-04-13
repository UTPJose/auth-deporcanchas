package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.User;

import java.util.List;

public class GetAllUserUseCase {

    private final UserRepositoryPort userRepository;

    public GetAllUserUseCase(
            UserRepositoryPort userRepository
    ) {
        this.userRepository = userRepository;
    }

    public PageResult<User> execute(int page, int size) {
        return userRepository.findAll(page, size);
    }
}