package com.synex.synexuserservice.controller;

import com.synex.synexuserservice.dto.request.DeleteLinkedUserIdentityRequestDto;
import com.synex.synexuserservice.dto.request.LinkUserIdentityRequestDto;
import com.synex.synexuserservice.dto.response.UserIdentityResponseDto;
import com.synex.synexuserservice.dto.security.Auth0IdentityDto;
import com.synex.synexuserservice.dto.security.AuthenticatedUserIdentityDto;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import com.synex.synexuserservice.service.UserIdentityService;
import com.synex.synexuserservice.service.support.AccountLinkingService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/identities")
public class UserIdentityController {

    private final UserIdentityService userIdentityService;
    private final AuthenticateService authenticateService;
    private final AccountLinkingService accountLinkingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserIdentityResponseDto> getAllUserIdentities(Authentication authentication) {
        return userIdentityService.getAllUserIdentities(authenticateService.getUserByAuthenticatedIdentity(authentication).getPublicId());
    }

    @PostMapping("/link")
    @ResponseStatus(HttpStatus.OK)
    public void linkUserAccount(@Valid @RequestBody LinkUserIdentityRequestDto request, Authentication authentication) {

        UserIdentityEntity identity = accountLinkingService.linkUserAccount(
                AuthenticatedUserIdentityDto.from(authentication).subject(),
                request.idToken(),
                authenticateService.requireAuthenticatedUserPublicId(authentication)
        );

        userIdentityService.saveUserIdentity(identity);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/link")
    public void deleteLinkedUserAccount(@Valid @RequestBody DeleteLinkedUserIdentityRequestDto request, Authentication authentication) {
        Auth0IdentityDto linkedUserIdentity = accountLinkingService.deleteLinkedUserAccount(
                AuthenticatedUserIdentityDto.from(authentication).subject(),
                IdentityProvider.valueOf(request.provider()).getName()
        );

        UserIdentityEntity identity = userIdentityService.findByUserIdAndProviderAndSubject(
                authenticateService.requireAuthenticatedUserPublicId(authentication),
                IdentityProvider.fromName(linkedUserIdentity.provider()),
                linkedUserIdentity.subject()
        );

        userIdentityService.deleteUserIdentity(identity);
    }
}
