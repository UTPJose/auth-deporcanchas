package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeRoleRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.fakerepository.FakeUserRepository;
import com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder.UserTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserUseCaseTest {

    private FakeUserRepository userRepository;
    private FakeRoleRepository roleRepository;

    private CreateUserUseCase createUserUseCase;

    User user;

    @BeforeEach
    public void setUp() {
        userRepository = new FakeUserRepository();
        roleRepository = new FakeRoleRepository();

        createUserUseCase = new CreateUserUseCase(
                userRepository,
                roleRepository
        );

        user = UserTestBuilder.builder().build();

    }

    @Test
    void create_user_successfully() {

        User result = createUserUseCase.execute(user);

        Optional<User> savedUser = userRepository.findById(result.getId());

        assertAll(
                () -> assertNotNull(result.getId()),
                () -> assertTrue(savedUser.isPresent()),
                () -> assertNotNull(result.getRole()),
                () -> assertEquals(result.getRole().getNombre(), "ROLE_ADMIN")
        );
    }

    @Test
    void throw_exception_when_email_already_exist() {
        userRepository.add(user);

        assertThrows(IllegalArgumentException.class,
                () -> createUserUseCase.execute(user));
    }

    @Test
    void throw_exception_when_user_has_no_roles() {
        user.setRole(null);

        assertThrows(IllegalArgumentException.class,
                () -> createUserUseCase.execute(user));
    }

    @Test
    void throw_exception_when_user_role_is_not_allowed() {
        user.setRole(new Role(3L, "ROLE_NONEXISTENT"));

        assertThrows(IllegalArgumentException.class,
                () -> createUserUseCase.execute(user));
    }

}