package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.controller;

import com.jdesprout.auth.auth_app_backend.application.usecase.user.*;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.AllUsersDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserByRoleDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper.UserDTOMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper.UserWebMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.request.UpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final CreateUserUseCase createUser;
    private final GetAllUserUseCase getAllUser;
    private final GetAllUserByRoleUseCase getAllUserByRole;
    private final GetUserByEmailUseCase getUserByEmail;
    private final GetUserByIdUseCase getUserById;
    private final DeleteUserUseCase deleteUser;
    private final UpdateUserUseCase updateUser;

    private final UserDTOMapper dtoMapper;
    private final UserWebMapper webMapper;

    public UserController(CreateUserUseCase createUser,
                          GetAllUserUseCase getAllUser,
                          GetAllUserByRoleUseCase getAllUserByRole,
                          GetUserByEmailUseCase getUserByEmail,
                          GetUserByIdUseCase getUserById,
                          DeleteUserUseCase deleteUser,
                          UpdateUserUseCase updateUser,
                          UserWebMapper userWebMapper,
                          UserDTOMapper dtoMapper) {
        this.createUser = createUser;
        this.getAllUser = getAllUser;
        this.getAllUserByRole = getAllUserByRole;
        this.getUserByEmail = getUserByEmail;
        this.getUserById = getUserById;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.webMapper = userWebMapper;
        this.dtoMapper = dtoMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO request) {
        User user = createUser.execute(dtoMapper.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoMapper.toDTO(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageResult<AllUsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            )
    {
        PageResult<User> result = getAllUser.execute(page, size);
        return ResponseEntity
                .ok(webMapper.toDTOPageResult(result));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-role")
    public ResponseEntity<PageResult<UserByRoleDTO>> getAllUsersByRoleUser(
            @RequestParam(defaultValue = "ROLE_USER") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageResult<User> result = getAllUserByRole.execute(role, page, size);

        return ResponseEntity
                .ok(webMapper.toDTOPageResultByRole(result));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(
            @RequestParam String email
    ) {
        User user = getUserByEmail.execute(email);
        return ResponseEntity
                .ok(dtoMapper.toDTO(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-id")
    public ResponseEntity<UserDTO> getUserByUserId(
            @RequestParam Long id
    ) {
        User user = getUserById.execute(id);
        return ResponseEntity
                .ok(dtoMapper.toDTO(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/by-name")
    public void deleteUser(
            @RequestParam String name
    ) {
        deleteUser.execute(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UpdateRequest request
    ) {
        User user = updateUser.execute(dtoMapper.toDomain(request));
        return ResponseEntity
                .ok(dtoMapper.toDTO(user));
    }

}
