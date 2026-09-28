package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginTrackingService {

    private final AuthenticateService authenticateService;

    @Transactional
    public void recordLoginIfNecessary(Authentication authentication) {

        Optional<UserIdentityEntity> identity = authenticateService.getAuthenticatedIdentity(authentication);

        if (identity.isEmpty()) return;

        UserEntity user = authenticateService.getUserByAuthenticatedIdentity(authentication);
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Instant loginTime = Objects.requireNonNull(jwt).getIssuedAt();

        if (loginTime == null) return;

        if (user.getLastLoginAt() == null || loginTime.isAfter(user.getLastLoginAt())) {
            user.updateLastLogin();
            identity.get().updateLastUsedAt();
        }
    }
}