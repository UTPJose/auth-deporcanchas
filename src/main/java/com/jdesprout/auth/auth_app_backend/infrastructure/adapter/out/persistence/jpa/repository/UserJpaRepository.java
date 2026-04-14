package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    
    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByName(String name);
    boolean existsByEmail(String email);

    @Query("""
        SELECT u
        FROM UserJpaEntity u
        JOIN u.roles r
        WHERE r.name = :roleName
    """)
    Page<UserJpaEntity> findByRole(@Param("roleName") String role, Pageable pageable);
}
