package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.dto.security.AuthenticatedUserIdentityDto;
import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import com.synex.synexuserservice.exception.UserIdentityNotFoundException;
import com.synex.synexuserservice.service.UserIdentityService;
import com.synex.synexuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserService userService;
    private final UserIdentityService userIdentityService;

    public UserEntity getUserByAuthenticatedIdentity(Authentication authentication) {
        return userService.findByPublicId(getAuthenticatedIdentity(authentication).map(UserIdentityEntity::getUserId)
                .orElseThrow(() -> new UserIdentityNotFoundException("User identity not found.")));
    }

    public Optional<UUID> getAuthenticatedUserPublicId(Authentication authentication) {
        return getAuthenticatedIdentity(authentication).map(UserIdentityEntity::getUserId);
    }

    public UUID requireAuthenticatedUserPublicId(Authentication authentication) {
        return getAuthenticatedUserPublicId(authentication).orElseThrow(() -> new UserIdentityNotFoundException("User identity not found."));
    }

    public Optional<UserIdentityEntity> getAuthenticatedIdentity(Authentication authentication) {
        AuthenticatedUserIdentityDto authenticatedUser = AuthenticatedUserIdentityDto.from(authentication);

        return userIdentityService.getAuthenticatedUserIdentityProvider(
                IdentityProvider.valueOf(authenticatedUser.provider()),
                authenticatedUser.subject());
    }
}
