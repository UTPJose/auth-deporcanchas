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
@Table(name = "users")
public class UserJpaEntity {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_name", length = 500)
    private String name;
    private String password;
    @Builder.Default
    private boolean enable = true;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updateAt = Instant.now();
    @Enumerated(EnumType.STRING)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleJpaEntity> roles = new HashSet<>();

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
