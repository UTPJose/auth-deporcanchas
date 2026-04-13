package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;

public class GetAllUserByRoleUseCase {

    private final UserRepositoryPort userRepository;

    public GetAllUserByRoleUseCase(
            UserRepositoryPort userRepository
    ) {
        this.userRepository = userRepository;
    }

    public PageResult<User> execute(String role, int page, int size) {
        return userRepository.findByRole(role, page, size);
    }
}
