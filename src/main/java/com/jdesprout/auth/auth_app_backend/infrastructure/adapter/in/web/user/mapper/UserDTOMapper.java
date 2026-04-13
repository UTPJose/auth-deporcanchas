package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.RoleDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDTOMapper {

    public User toDomain(UserDTO dto) {
        return new User(
                dto.getId(),
                new Email(dto.getEmail()),
                dto.getName(),
                dto.getPassword(),
                dto.getImage(),
                dto.isEnable(),
                dto.getCreatedAt(),
                dto.getUpdateAt(),
                mapRoles(dto.getRoles())
        );
    }

    private Set<Role> mapRoles(Set<RoleDTO> roles) {
        return roles.stream()
                .map(role -> new Role(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }

    public UserDTO toDTO(User domain) {

        Set<RoleDTO> roleEntities = domain.getRoles() == null
                ? Set.of()
                : domain.getRoles().stream()
                .map(role -> {
                    RoleDTO dto = new RoleDTO();
                    dto.setId(role.getId());
                    dto.setName(role.getName());
                    return dto;
                })
                .collect(Collectors.toSet());

        return new UserDTO(
                domain.getId(),
                domain.getEmail().value(),
                domain.getName(),
                domain.getPassword(),
                domain.getImage(),
                domain.isEnable(),
                domain.getCreatedAt(),
                domain.getUpdateAt(),
                roleEntities
        );
    }

}
