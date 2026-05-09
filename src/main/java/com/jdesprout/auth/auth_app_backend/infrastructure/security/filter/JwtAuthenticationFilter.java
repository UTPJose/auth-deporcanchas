package com.jdesprout.auth.auth_app_backend.infrastructure.security.filter;

import com.jdesprout.auth.auth_app_backend.infrastructure.adapter.out.persistence.jpa.repository.UserJpaRepository;
import com.jdesprout.auth.auth_app_backend.infrastructure.security.jwt.JwtService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserJpaRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                if(!jwtService.isAccessToken(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Jws<Claims> parse = jwtService.parse(token);
                Claims payload = parse.getPayload();
                String userIdStr = payload.getSubject();
                Long userId = Long.parseLong(userIdStr);

                userRepository.findById(userId)
                        .ifPresent(user -> {
                            if(user.isEnable()) {
                                List<GrantedAuthority> authorities = user.getRole() == null
                                        ? List.of()
                                        : List.of(new SimpleGrantedAuthority(user.getRole().getNombre()));

                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        user.getEmail(),
                                        null,
                                        authorities

                                );

                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                if(SecurityContextHolder.getContext().getAuthentication() == null)
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                            }
                        });
            } catch (ExpiredJwtException e) {
                request.setAttribute("error", "Token Expirado");

            } catch (Exception e) {
                request.setAttribute("error", "Token Inválido");

            }
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/v1/auth/");
    }
}
