package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos;

import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable = true;
    private Instant createdAt = Instant.now();
    private Instant updateAt = Instant.now();
    private Set<RoleDTO> roles = new HashSet<>();

}
