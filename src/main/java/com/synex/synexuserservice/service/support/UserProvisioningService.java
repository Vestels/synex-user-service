package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.dto.response.UserResponseDto;
import com.synex.synexuserservice.dto.security.AuthenticatedUserDto;
import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.entity.UserPreferencesEntity;
import com.synex.synexuserservice.entity.UserProfileEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import com.synex.synexuserservice.exception.AccountLinkRequiredException;
import com.synex.synexuserservice.exception.Auth0UserNotFoundException;
import com.synex.synexuserservice.service.UserIdentityService;
import com.synex.synexuserservice.service.UserPreferencesService;
import com.synex.synexuserservice.service.UserProfileService;
import com.synex.synexuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProvisioningService {

    private final UserProfileService userProfileService;
    private final UserPreferencesService userPreferencesService;
    private final UserIdentityService userIdentityService;
    private final UserService userService;
    private final Auth0ManagementService auth0ManagementService;
    private final AuthenticateService authenticateService;

    @Transactional
    public UserResponseDto getOrProvisionUser(Authentication authentication) {

        AuthenticatedUserDto authenticatedUser = AuthenticatedUserDto.from(authentication);
        UserIdentityEntity identity = authenticateService.getAuthenticatedIdentity(authentication).orElse(null);

        if (identity != null) {
            return UserResponseDto.from(userService.findByPublicId(identity.getUserId()));
        }

        if (userService.findByEmail(authenticatedUser.email()).isPresent()) {
            throw new AccountLinkRequiredException(
                    "An account with this email already exists. Account linking is required.");
        }

        if (!auth0ManagementService.userExists(authenticatedUser.subject())) {
            throw new Auth0UserNotFoundException("This Account has been deleted.");
        }

        return createUser(
                authenticatedUser.subject(),
                authenticatedUser.email(),
                IdentityProvider.valueOf(authenticatedUser.provider())
        );
    }

    private UserResponseDto createUser(String subject, String email, IdentityProvider provider) {

        UserEntity user = new UserEntity(email);
        user.updateLastLogin();
        user.updateLastActivityAt();

        UserIdentityEntity userIdentity = new UserIdentityEntity(user.getPublicId(), provider, subject);
        userIdentity.updateLastUsedAt();

        userService.saveUser(user);
        userProfileService.saveUserProfile(new UserProfileEntity(user.getPublicId()));
        userPreferencesService.saveUserPreferences(new UserPreferencesEntity(user.getPublicId()));
        userIdentityService.saveUserIdentity(userIdentity);

        return UserResponseDto.from(user);
    }
}
