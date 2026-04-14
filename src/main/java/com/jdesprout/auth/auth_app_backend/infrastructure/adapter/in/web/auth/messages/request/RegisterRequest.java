package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request;

import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.RoleDTO;

import java.util.Set;

public record RegisterRequest(
        String email,
        String name,
        String password,
        Set<RoleDTO> roles
) {
}
