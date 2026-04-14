package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.RoleJpaEntity;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserJpaEntity jpaEntity) {
        return new User(
                jpaEntity.getId(),
                new Email(jpaEntity.getEmail()),
                jpaEntity.getName(),
                jpaEntity.getPassword(),
                jpaEntity.isEnable(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdateAt(),
                mapRoles(jpaEntity.getRoles())
        );
    }

    private Set<Role> mapRoles(Set<RoleJpaEntity> roles) {
        return roles.stream()
                .map(role -> new Role(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }

    public UserJpaEntity toEntity(User domain) {

        Set<RoleJpaEntity> roleEntities = domain.getRoles() == null
                ? Set.of()
                : domain.getRoles().stream()
                .map(role -> {
                    RoleJpaEntity entity = new RoleJpaEntity();
                    entity.setId(role.getId());
                    entity.setName(role.getName());
                    return entity;
                })
                .collect(Collectors.toSet());

        return UserJpaEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail().value())
                .name(domain.getName())
                .password(domain.getPassword())
                .enable(domain.isEnable())
                .createdAt(domain.getCreatedAt())
                .updateAt(domain.getUpdateAt())
                .roles(roleEntities)
                .build();
    }
}
