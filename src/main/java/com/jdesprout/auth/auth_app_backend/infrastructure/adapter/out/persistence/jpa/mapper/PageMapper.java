package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.jdesprout.auth.auth_app_backend.domain.port.user.PageResult;
import org.springframework.data.domain.Page;

public class PageMapper {
    public static <T> PageResult<T> toPageResult(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
