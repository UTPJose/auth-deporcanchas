package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CreateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;

    public CreateUserUseCase(
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User execute(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ese correo ya existe, intenta con otro");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Debe asignarse al menos un rol (ADMIN o USER)");
        }

        user.setId(UUID.randomUUID().toString());
        user.setEnable(true);

        Set<Role> validatedRoles = new HashSet<>();

        String allowedRoles = roleRepository.findAll()
                .stream()
                .map(Role::getName)
                .toList()
                .toString();

        for (Role role : user.getRoles()) {
            String roleName = role.getName().trim();
            Role _role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Rol no permitido: '" + roleName + "'. Roles permitidos: " + allowedRoles
                    ));
            validatedRoles.add(_role);
        }

        user.setRoles(validatedRoles);

        return userRepository.save(user);
    }

}
