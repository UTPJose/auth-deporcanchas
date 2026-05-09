package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {

    private Long id;
    private String nombre;

}
