package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.AllUsersDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserByRoleDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWebMapper {

    public PageResult<AllUsersDTO> toDTOPageResult(PageResult<User> page) {

        List<AllUsersDTO> dtoList = page.content().stream()
                .map(u -> AllUsersDTO.builder()
                        .id(u.getId())
                        .email(u.getEmail().value())
                        .name(u.getName())
                        .createdAt(u.getCreatedAt())
                        .updateAt(u.getUpdateAt())
                        .build()
                )
                .toList();

        return new PageResult<>(
                dtoList,
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }

    public PageResult<UserByRoleDTO> toDTOPageResultByRole(PageResult<User> page) {

        List<UserByRoleDTO> dtoList = page.content().stream()
                .map(u -> UserByRoleDTO.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail().value())
                        .createdAt(u.getCreatedAt())
                        .updateAt(u.getUpdateAt())
                        .build()
                )
                .toList();

        return new PageResult<>(
                dtoList,
                page.page(),
                page.size(),
                page.totalElements(),
                page.totalPages()
        );
    }

}
