package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.dto.security.Auth0IdentityDto;
import com.synex.synexuserservice.dto.security.Auth0UserResponseDto;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import com.synex.synexuserservice.exception.AccountLinkFailedException;
import com.synex.synexuserservice.exception.Auth0UserNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class AccountLinkingService {

    private final Auth0ManagementService auth0ManagementService;
    private final JwtDecoder secondaryJwtDecoder;

    public AccountLinkingService(
            Auth0ManagementService auth0ManagementService,
            @Qualifier("accountLinkingJwtDecoder") JwtDecoder accountLinkingJwtDecoder
    ) {
        this.auth0ManagementService = auth0ManagementService;
        this.secondaryJwtDecoder = accountLinkingJwtDecoder;
    }

    public Auth0IdentityDto deleteLinkedUserAccount(String auth0UserId, String provider) {
        try {

            Auth0UserResponseDto auth0User = auth0ManagementService.getUser(auth0UserId)
                    .orElseThrow(() -> new Auth0UserNotFoundException("Auth0 user not found."));

            Auth0IdentityDto linkedIdentity = auth0User.identities()
                    .stream()
                    .filter(identity ->
                            identity.provider().equals(provider))
                    .findFirst()
                    .orElseThrow(() -> new AccountLinkFailedException("An unexpected error occurred while deleting the identity. Please try again."));

            auth0ManagementService.deleteLinkedAccount(auth0UserId, linkedIdentity.provider(), linkedIdentity.subject());
            auth0ManagementService.deleteUser(String.format("%s|%s", linkedIdentity.provider(), linkedIdentity.subject()));

            return linkedIdentity;

        } catch (HttpClientErrorException exception) {
            throw new AccountLinkFailedException("An unexpected error occurred while deleting the account. Please try again.");
        }
    }

    public UserIdentityEntity linkUserAccount(String auth0UserId, String secondaryIdToken, UUID publicId) {
        try {
            Jwt secondaryJwt = secondaryJwtDecoder.decode(secondaryIdToken);
            String secondaryUserId = secondaryJwt.getSubject();

            if (secondaryUserId == null || secondaryUserId.isBlank()) {
                throw new AccountLinkFailedException(
                        "The secondary identity token does not contain a valid subject."
                );
            }

            auth0ManagementService.linkAccount(auth0UserId, extractProvider(secondaryUserId), extractProviderUserId(secondaryUserId));

            Auth0UserResponseDto auth0User = auth0ManagementService.getUser(auth0UserId)
                    .orElseThrow(() -> new Auth0UserNotFoundException("Auth0 user not found."));

            Auth0IdentityDto linkedIdentity = auth0User.identities()
                    .stream()
                    .filter(identity ->
                            identity.provider().equals(extractProvider(secondaryUserId))
                                    && identity.subject().equals(extractProviderUserId(secondaryUserId))
                    )
                    .findFirst()
                    .orElseThrow(() -> new AccountLinkFailedException("An unexpected error occurred while linking the accounts. Please try again."));

            UserIdentityEntity userIdentity = new UserIdentityEntity(
                    publicId,
                    IdentityProvider.fromName(linkedIdentity.provider()),
                    linkedIdentity.subject()
            );

            userIdentity.updateLastUsedAt();

            return userIdentity;

        } catch (HttpClientErrorException exception) {
            throw new AccountLinkFailedException("An unexpected error occurred while linking the accounts. Please try again.");
        }
    }


    public String extractProvider(String userId) {
        return userId.split("\\|")[0];
    }

    public String extractProviderUserId(String userId) {
        return userId.split("\\|")[1];
    }
}
