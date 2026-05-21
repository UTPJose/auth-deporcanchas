package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "usuarios")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "nombre", length = 500, nullable = false)
    private String name;
    @Column(name = "clave_hash", nullable = false, length = 100)
    private String password;
    @Column(name= "dni", unique = true)
    private String dni;
    @Column(name = "celular", unique = true)
    private String phoneNumber;
    @Builder.Default
    @Column(name = "estaActivo", nullable = false)
    private boolean enable = true;
    @Builder.Default
    @Column(name = "creado_en", nullable = false)
    private Instant createdAt = Instant.now();
    @Builder.Default
    @Column(name = "actualizado_en")
    private Instant updateAt = Instant.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id", nullable = false)
    private RoleJpaEntity role;

    @PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updateAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = Instant.now();
    }

}
