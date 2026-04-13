package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.jdesprout.auth.auth_app_backend.domain.port.user.RoleRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.RoleJpaEntity;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper.RolePersistenceMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleRepository;
    private final RolePersistenceMapper mapper;

    @Override
    public Optional<Role> findByName(String rolName) {
        return roleRepository.findByName(rolName);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = mapper.toEntity(role);
        RoleJpaEntity saved = roleRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public boolean existsByName(String rolName) {
        return roleRepository.existsByName(rolName);
    }
}
