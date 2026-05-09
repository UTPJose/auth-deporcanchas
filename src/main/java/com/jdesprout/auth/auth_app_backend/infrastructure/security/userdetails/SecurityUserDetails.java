package com.jdesprout.auth.auth_app_backend.infrastructure.security.userdetails;

import com.jdesprout.auth.auth_app_backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SecurityUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole() == null
                ? List.of()
                : List.of(new SimpleGrantedAuthority(user.getRole().getNombre()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail().value();
    }


    @Override
    public boolean isEnabled() {
        return user.isEnable();
    }

    public Long getUserId(){
        return user.getId();
    }

}
