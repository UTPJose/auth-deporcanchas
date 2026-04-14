package com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.controller;

import com.jdesprout.auth.auth_app_backend.application.usecase.auth.LoginUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.RefreshTokenUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.RegisterUserUseCase;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.query.LoginQuery;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.LoginResult;
import com.jdesprout.auth.auth_app_backend.application.usecase.auth.result.RefreshTokenResult;
import com.jdesprout.auth.auth_app_backend.domain.model.Role;
import com.jdesprout.auth.auth_app_backend.domain.model.User;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request.LoginRequest;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request.RefreshTokenRequest;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.request.RegisterRequest;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.response.TokenResponse;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.auth.messages.response.UserResponse;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.dtos.UserDTO;
import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.in.web.user.mapper.UserDTOMapper;
import com.jdesprout.auth.auth_app_backend.infrastructure.security.cookie.CookieService;
import com.jdesprout.auth.auth_app_backend.infrastructure.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final RegisterUserUseCase registerUser;

    private final JwtService jwtService;
    private final UserDTOMapper mapper;
    private final CookieService cookieService;


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login (
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

        LoginResult result = loginUseCase.execute(
                new LoginQuery(request.email(), request.password())
        );

        cookieService.attachRefreshCookie(
                response,
                result.refreshToken(),
                (int) jwtService.getRefreshTtlSeconds()
        );

        cookieService.addNoStoreHeaders(response);

        TokenResponse tokenResponse = TokenResponse.of(
                result.accessToken(),
                result.refreshToken(),
                jwtService.getAccessTtlSeconds(),
                UserResponse.of(
                        result.user().getEmail().value(),
                        result.user().getName(),
                        result.user().getPassword(),
                        result.user().isEnable(),
                        result.user().getRoles().stream()
                                .map(Role::getName)
                                .findFirst()
                                .orElseThrow(null)
                )
        );

        return ResponseEntity.ok(tokenResponse);

    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestBody(required = false) RefreshTokenRequest body,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws com.jdesprout.auth.auth_app_backend.application.exception.BadCredentialsException {

        String refreshToken = readRefreshTokenFromRequest(body, request)
                .orElseThrow(() -> new BadCredentialsException("Invalid Refresh Token"));

        RefreshTokenResult result = refreshTokenUseCase.execute(refreshToken);

        cookieService.attachRefreshCookie(
                response,
                result.refreshToken(),
                (int) jwtService.getRefreshTtlSeconds()
        );

        cookieService.addNoStoreHeaders(response);

        return ResponseEntity.ok(
                TokenResponse.of(
                        result.accessToken(),
                        result.refreshToken(),
                        result.expiresIn(),
                        UserResponse.of(
                                result.user().getEmail().value(),
                                result.user().getName(),
                                result.user().getPassword(),
                                result.user().isEnable(),
                                result.user().getRoles().stream()
                                        .map(Role::getName)
                                        .findFirst()
                                        .orElseThrow(null)
                        )
                )
        );

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        cookieService.clearRefreshCookie(response);
        cookieService.addNoStoreHeaders(response);
        SecurityContextHolder.clearContext();

        return ResponseEntity.noContent().build();
    }

    private Optional<String> readRefreshTokenFromRequest(RefreshTokenRequest body, HttpServletRequest request) {
        if (request.getCookies() != null) {
            Optional<String> fromCookie = Arrays.stream(request.getCookies())
                    .filter(c -> cookieService.getRefreshTokenCookieName().equals(c.getName()))
                    .map(Cookie::getValue)
                    .filter(v -> !v.isBlank())
                    .findFirst();

            if (fromCookie.isPresent()) {
                return fromCookie;
            }

        }

        if (body != null && body.refreshToken() != null && !body.refreshToken().isBlank()) {
            return Optional.of(body.refreshToken());
        }

        String refreshHeader = request.getHeader("X-Refresh-Token");
        if (refreshHeader != null && !refreshHeader.isBlank()) {
            return Optional.of(refreshHeader.trim());
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
            String candidate = authHeader.substring(7).trim();
            if (!candidate.isEmpty()) {
                try {
                    if (jwtService.isRefreshToken(candidate)) {
                        return Optional.of(candidate);
                    }
                } catch (Exception ignored) {}
            }
        }

        return Optional.empty();

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest request) {
        User user = registerUser.execute(mapper.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.of(
                        user.getEmail().value(),
                        user.getName(),
                        user.getPassword(),
                        user.isEnable(),
                        user.getRoles().stream()
                                .map(Role::getName)
                                .findFirst()
                                .orElseThrow(null)
                ));
    }

}
