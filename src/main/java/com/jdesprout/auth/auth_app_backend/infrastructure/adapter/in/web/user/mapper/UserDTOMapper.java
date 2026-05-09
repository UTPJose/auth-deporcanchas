package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request.RegisterRequest;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request.RoleRequest;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.RoleDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.request.UpdateRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
public class UserDTOMapper {

    public User toDomain(UserDTO dto) {
        Role role = dto.getRole() != null
                ? new Role(dto.getRole().getId(), dto.getRole().getNombre())
                : null;

        return new User(
                dto.getId(),
                new Email(dto.getEmail()),
                dto.getName(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                dto.isEnable(),
                dto.getCreatedAt(),
                dto.getUpdateAt(),
                role
        );
    }

    public User toDomain(UpdateRequest request) {
        return new User(
                null,
                new Email(request.email()),
                request.name(),
                request.password(),
                request.phoneNumber(),
                request.enabled(),
                null,
                null,
                null
        );
    }

    public User toDomain(RegisterRequest request) {
        Role role = null;
        if (request.roles() != null && !request.roles().isEmpty()) {
            RoleRequest roleRequest = request.roles().stream().findFirst().orElse(null);
            if (roleRequest != null) {
                role = new Role(null, roleRequest.roleName());
            }
        }

        return new User(
                null,
                new Email(request.email()),
                request.name(),
                request.password(),
                request.phoneNumber(),
                true,
                Instant.now(),
                null,
                role
        );
    }

    public UserDTO toDTO(User domain) {
        RoleDTO roleDTO = null;
        if (domain.getRole() != null) {
            roleDTO = new RoleDTO();
            roleDTO.setId(domain.getRole().getId());
            roleDTO.setNombre(domain.getRole().getNombre());
        }

        return new UserDTO(
                domain.getId(),
                domain.getEmail().value(),
                domain.getName(),
                domain.getPassword(),
                domain.getPhoneNumber(),
                domain.isEnable(),
                domain.getCreatedAt(),
                domain.getUpdateAt(),
                domain.getRole() != null ? roleDTO : null
        );
    }

}
