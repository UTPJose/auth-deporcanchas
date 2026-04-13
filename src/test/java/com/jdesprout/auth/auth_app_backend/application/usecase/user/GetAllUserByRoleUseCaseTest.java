package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetAllUserByRoleUseCaseTest {

    private FakeUserRepository userRepository;

    private GetAllUserByRoleUseCase getAllUserByRoleUseCase;

    User admin = UserTestBuilder.builder().build();
    User user = UserTestBuilder.builder().withUserRole().build();

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();

        getAllUserByRoleUseCase = new GetAllUserByRoleUseCase(
                userRepository
        );
    }

    @Test
    void users_filtered_by_role_successfully() {
        userRepository.add(admin);
        userRepository.add(user);

        PageResult<User> result =
                getAllUserByRoleUseCase.execute("ROLE_ADMIN", 0, 10);

        assertEquals(1, result.content().size());
    }


}