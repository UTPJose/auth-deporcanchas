package com.jdesprout.auth.auth_app_backend.domain.port.user;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
