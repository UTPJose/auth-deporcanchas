package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleJpaEntity {
    @Id
    @Column(name = "role_id")
    private String id;
    @Column(unique = true, nullable = false)
    private String name;
}
