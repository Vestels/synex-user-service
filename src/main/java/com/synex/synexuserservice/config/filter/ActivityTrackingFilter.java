package com.synex.synexuserservice.config.filter;

import com.synex.synexuserservice.service.UserService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityTrackingFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final AuthenticateService authenticateService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken authentication) {
            Optional<UUID> publicId = authenticateService.getAuthenticatedUserPublicId(authentication);

            publicId.ifPresent(userService::updateUserLastActivityAt);
        }

        filterChain.doFilter(request, response);
    }
}
