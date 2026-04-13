package com.jdesprout.auth.auth_app_backend.domain.model;

import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;

import java.time.Instant;
import java.util.Set;

public class User {

    private String id;
    private Email email;
    private String name;
    private String password;
    private String image;
    private boolean enable;
    private Instant createdAt;
    private Instant updateAt;
    private Set<Role> roles;

    public User(
            String id,
            Email email,
            String name,
            String password,
            String image,
            boolean enable,
            Instant createdAt,
            Instant updateAt,
            Set<Role> roles
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
        this.enable = enable;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
