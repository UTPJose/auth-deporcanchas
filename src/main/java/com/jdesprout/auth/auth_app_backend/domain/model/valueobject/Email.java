package com.jdesprout.auth.auth_app_backend.domain.model.valueobject;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");

    public Email {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!emailPattern.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid Email Format");
        }
        value = value.toLowerCase();
    }

}
