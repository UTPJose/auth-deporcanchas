package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserByRoleDTO {

    private String id;
    private String name;
    private String email;
    private Instant createdAt = Instant.now();
    private Instant updateAt = Instant.now();

}
