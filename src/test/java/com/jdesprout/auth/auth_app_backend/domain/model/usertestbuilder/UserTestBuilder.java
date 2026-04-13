package com.jdesprout.auth.auth_app_backend.domain.model.usertestbuilder;

import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.domain.model.valueobject.Email;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class UserTestBuilder {
    private String id = "user-id";
    private Email email = new Email("user@test.com");
    private String name = "User";
    private String password = "123456";
    private String image = null;
    private boolean enable = true;
    private Instant createdAt = Instant.now();
    private Instant updateAt = Instant.now();
    private Set<Role> roles = new HashSet<>(Set.of(
            new Role("1", "ROLE_ADMIN")
    ));

    public static UserTestBuilder builder() {
        return new UserTestBuilder();
    }

    public UserTestBuilder email(Email email) {
        this.email = email;
        return this;
    }

    public UserTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserTestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserTestBuilder image(String image) {
        this.image = image;
        return this;
    }

    public UserTestBuilder enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public UserTestBuilder withUserRole() {
        this.roles = Set.of(new Role( "2", "ROLE_USER"));
        return this;
    }

    public User build() {
        return new User(id, email, name, password, image, enable, createdAt, updateAt, roles);
    }
}
