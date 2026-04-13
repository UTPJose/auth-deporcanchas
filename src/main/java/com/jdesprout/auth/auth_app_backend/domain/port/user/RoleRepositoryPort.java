package com.jdesprout.auth.auth_app_backend.domain.port.user;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String rolName);
    List<Role> findAll();
    Role save(Role role);
    boolean existsByName(String rolName);
}
