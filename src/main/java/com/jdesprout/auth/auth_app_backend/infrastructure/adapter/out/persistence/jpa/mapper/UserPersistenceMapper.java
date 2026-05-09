package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.RoleJpaEntity;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserJpaEntity jpaEntity) {
        Role role = jpaEntity.getRole() != null
                ? new Role(jpaEntity.getRole().getId(), jpaEntity.getRole().getNombre())
                : null;

        return new User(
                jpaEntity.getId(),
                new Email(jpaEntity.getEmail()),
                jpaEntity.getName(),
                jpaEntity.getPassword(),
                jpaEntity.getPhoneNumber(),
                jpaEntity.isEnable(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdateAt(),
                role
        );
    }

    public UserJpaEntity toEntity(User domain) {
        RoleJpaEntity roleEntity = null;
        if (domain.getRole() != null) {
            roleEntity = new RoleJpaEntity();
            roleEntity.setId(domain.getRole().getId());
            roleEntity.setNombre(domain.getRole().getNombre());
        }

        return UserJpaEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail().value())
                .name(domain.getName())
                .password(domain.getPassword())
                .phoneNumber(domain.getPhoneNumber())
                .enable(domain.isEnable())
                .createdAt(domain.getCreatedAt())
                .updateAt(domain.getUpdateAt())
                .role(roleEntity)
                .build();
    }
}
