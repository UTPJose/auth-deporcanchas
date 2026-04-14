package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseTest {

    private FakeUserRepository userRepository;

    DeleteUserUseCase deleteUserUseCase;

    User user;

    @BeforeEach
    void setUp() {

        userRepository = new FakeUserRepository();

        deleteUserUseCase = new DeleteUserUseCase(
                userRepository
        );

        user = UserTestBuilder.builder().build();
    }

    @Test
    void delete_user_successfully() {
        userRepository.add(user);

        deleteUserUseCase.execute(user.getName());

        Optional<User> deletedUser = userRepository.findById(user.getId());

        assertTrue(deletedUser.isEmpty());
    }

    @Test
    void throws_exception_when_userid_not_exists() {
        userRepository.findById(user.getId());

        assertThrows(ResourceNotFoundException.class,
                () -> deleteUserUseCase.execute(user.getId()));
    }

}