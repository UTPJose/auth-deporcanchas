package com.jdesprout.auth.auth_app_backend.domain.port.user;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByEmail(Email email);
    void delete(User user);
    PageResult<User> findAll(int page, int size);
    PageResult<User> findByRole(String role, int page, int size);

}
