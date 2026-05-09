package com.jdesprout.auth.auth_app_backend.domain.model.fakerepository;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserRepository implements UserRepositoryPort {

    private final List<User> userdb = new ArrayList<>();

    public void add(User user) {
        userdb.add(user);
    }

    public void remove(User user) {
        userdb.remove(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userdb.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userdb.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userdb.stream()
                .filter(u -> u.getEmail().equals(new Email(email)))
                .findFirst();
    }

    @Override
    public User save(User user) {
        userdb.add(user);
        return user;
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userdb.stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public void delete(User user) {
        remove(user);
    }

    @Override
    public PageResult<User> findAll(int page, int size) {
        int totalElements = userdb.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageResult<>(
                userdb,
                page,
                size,
                totalElements,
                totalPages
        );
    }

    @Override
    public PageResult<User> findByRole(String role, int page, int size) {
        List<User> filtered =
                userdb.stream()
                        .filter(u -> u.getRole() != null && u.getRole().getNombre().equals(role))
                        .toList();

        int totalElements = filtered.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PageResult<>(
                filtered,
                page,
                size,
                totalElements,
                totalPages
        );
    }
}
