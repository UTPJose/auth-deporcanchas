package com.jdesprout.auth.auth_app_backend.domain.model;

import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;

import java.time.Instant;

public class User {

    private Long id;
    private Email email;
    private String name;
    private String password;
    private String phoneNumber;
    private boolean enable;
    private Instant createdAt;
    private Instant updateAt;
    private Role role;

    public User(
            Long id,
            Email email,
            String name,
            String password,
            String phoneNumber,
            boolean enable,
            Instant createdAt,
            Instant updateAt,
            Role role
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.enable = enable;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
