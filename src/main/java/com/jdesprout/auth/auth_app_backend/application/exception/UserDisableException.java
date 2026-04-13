package com.jdesprout.auth.auth_app_backend.application.exception;

public class UserDisableException extends RuntimeException {
    public UserDisableException() {
        super("User is disabled");
    }
}
