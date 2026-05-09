package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {
    Optional<RoleJpaEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
