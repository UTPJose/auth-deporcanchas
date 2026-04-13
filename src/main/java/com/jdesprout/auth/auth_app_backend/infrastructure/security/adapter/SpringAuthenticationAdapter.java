package com.jdesprout.auth.auth_app_backend.infrastructure.security.adapter;

import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticatedUser;
import com.jdesprout.auth.auth_app_backend.domain.port.auth.AuthenticationPort;
import com.jdesprout.auth.auth_app_backend.infrastructure.security.userdetails.SecurityUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SpringAuthenticationAdapter implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;

    public SpringAuthenticationAdapter(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticatedUser authenticate(String email, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );

        SecurityUserDetails principal =
                (SecurityUserDetails) authentication.getPrincipal();

        return new AuthenticatedUser(
                principal.getUserId(),
                principal.getUsername()
        );
    }
}
