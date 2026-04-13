package com.jdesprout.auth.auth_app_backend.domain.port.auth;

public interface AuthenticationPort {
    AuthenticatedUser authenticate(String email, String password);
}
