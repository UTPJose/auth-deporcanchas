package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.application.exception.ResourceNotFoundException;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserByEmailUseCaseTest {

    private FakeUserRepository userRepository;

    private GetUserByEmailUseCase getUserByEmailUseCase;

    User user;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();

        getUserByEmailUseCase = new GetUserByEmailUseCase(
                userRepository
        );

        user = UserTestBuilder.builder().build();
    }

    @Test
    void get_user_by_email_successfully() {

        userRepository.add(user);

        String email = "user@test.com";

        User result = getUserByEmailUseCase.execute(email);

        assertEquals(email, result.getEmail().value());

    }

    @Test
    void throw_exception_when_the_email_does_not_exist_in_repository() {
        String email = "user@test.com";

        assertThrows(ResourceNotFoundException.class,
                () -> getUserByEmailUseCase.execute(email));
    }

}