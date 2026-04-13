package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserByIdUseCaseTest {

    private FakeUserRepository userRepository;

    private GetUserByIdUseCase getUserByIdUseCase;

    User user;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();

        getUserByIdUseCase = new GetUserByIdUseCase(
                userRepository
        );

        user = UserTestBuilder.builder().build();
    }

    @Test
    void get_user_by_id_successfully() {

        userRepository.add(user);

        String userId = "user-id";

        User result = getUserByIdUseCase.execute(userId);

        assertEquals(userId, result.getId());

    }

    @Test
    void throw_exception_when_the_user_id_does_not_exist_in_repository() {
        String userId = "user_id";

        assertThrows(ResourceNotFoundException.class,
                () -> getUserByIdUseCase.execute(userId));
    }

}