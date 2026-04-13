package com.jdesprout.auth.auth_app_backend.domain.model.fakerepository;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeRoleRepository implements RoleRepositoryPort {

    List<Role> roledb = new ArrayList<>(
            List.of(
                    new Role("1", "ROLE_ADMIN"),
                    new Role("2", "ROLE_USER")
            )
    );

    @Override
    public Optional<Role> findByName(String rolName) {
        return roledb.stream()
                .filter(r -> r.getName().equals(rolName))
                .findFirst();
    }

    @Override
    public List<Role> findAll() {
        return roledb;
    }

    @Override
    public Role save(Role role) {
        roledb.add(role);
        return role;
    }

    @Override
    public boolean existsByName(String rolName) {
        return roledb.stream()
                .anyMatch(r -> r.getName().equals(rolName));
    }
}
