package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RolePersistenceMapper {

    public Role toDomain(RoleJpaEntity jpaEntity) {
        return new Role(
                jpaEntity.getId(),
                jpaEntity.getName()
        );
    }

    public RoleJpaEntity toEntity(Role domain) {
        return RoleJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

}
