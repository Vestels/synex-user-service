package com.synex.synexuserservice.dto.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

public record AuthenticatedUserIdentityDto(
        String subject,
        String provider,
        String email
) {

    public static AuthenticatedUserIdentityDto from(Authentication authentication) {
        Jwt jwt = (Jwt) Objects.requireNonNull(authentication.getPrincipal());

        return new AuthenticatedUserIdentityDto(
                jwt.getSubject(),
                jwt.getClaimAsString("identity_provider"),
                jwt.getClaimAsString("email")
        );
    }
}