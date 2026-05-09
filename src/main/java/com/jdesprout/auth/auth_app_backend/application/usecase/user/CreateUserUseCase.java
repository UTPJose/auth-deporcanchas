package com.jdesprout.auth.auth_app_backend.application.usecase.user;

import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;

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

        if (user.getRole() == null) {
            throw new IllegalArgumentException("Debe asignarse un rol (ADMIN o USER)");
        }

        user.setEnable(true);

        String allowedRoles = roleRepository.findAll()
                .stream()
                .map(Role::getNombre)
                .toList()
                .toString();

        String roleName = user.getRole().getNombre().trim();
        Role validatedRole = roleRepository.findByNombre(roleName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rol no permitido: '" + roleName + "'. Roles permitidos: " + allowedRoles
                ));

        user.setRole(validatedRole);

        return userRepository.save(user);
    }

}
