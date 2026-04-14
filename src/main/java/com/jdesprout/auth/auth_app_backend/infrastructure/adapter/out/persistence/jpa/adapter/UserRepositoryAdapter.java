package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;
import com.jdesprout.auth.auth_app_backend.domain.port.user.UserRepositoryPort;
import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.entity.UserJpaEntity;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper.PageMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper.UserPersistenceMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public Optional<User> findById(String id) {
        System.out.println("Primary repository");
        return userRepository.findById(id).map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name).map(userPersistenceMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity _user = userPersistenceMapper.toEntity(user);
        UserJpaEntity saved = userRepository.save(_user);
        return userPersistenceMapper.toDomain(saved);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userRepository.existsByEmail(email.toString());
    }

    @Override
    public void delete(User user) {
        UserJpaEntity _user = userPersistenceMapper.toEntity(user);
        userRepository.delete(_user);
    }

    @Override
    public PageResult<User> findAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> result = userRepository.findAll(pageable).map(userPersistenceMapper::toDomain);
        return PageMapper.toPageResult(result);
    }

    @Override
    public PageResult<User> findByRole(String role, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> result = userRepository.findByRole(role, pageable).map(userPersistenceMapper::toDomain);
        return PageMapper.toPageResult(result);
    }
}
