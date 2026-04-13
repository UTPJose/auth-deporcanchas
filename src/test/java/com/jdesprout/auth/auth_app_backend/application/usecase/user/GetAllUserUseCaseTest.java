package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetAllUserUseCaseTest {

    private FakeUserRepository userRepository;

    private GetAllUserUseCase getAllUserUseCase;

    User admin = UserTestBuilder.builder().build();
    User user = UserTestBuilder.builder().withUserRole().build();

    @BeforeEach
    void setUp() {

        userRepository = new FakeUserRepository();

        getAllUserUseCase = new GetAllUserUseCase(
                userRepository
        );
    }

    @Test
    void all_users_successfully() {
        userRepository.add(admin);
        userRepository.add(user);

        PageResult<User> result = getAllUserUseCase.execute(0, 10);

        assertEquals(2, result.content().size());
    }

}