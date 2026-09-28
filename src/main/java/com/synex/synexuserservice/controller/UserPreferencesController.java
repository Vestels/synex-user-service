package com.synex.synexuserservice.controller;

import com.synex.synexuserservice.dto.request.UpdateUserPreferencesRequestDto;
import com.synex.synexuserservice.dto.response.UserPreferencesAppBehaviourDto;
import com.synex.synexuserservice.dto.response.UserPreferencesResponseDto;
import com.synex.synexuserservice.exception.BadRequestException;
import com.synex.synexuserservice.service.UserPreferencesService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/preferences")
public class UserPreferencesController {

    private final UserPreferencesService userPreferencesService;
    private final AuthenticateService authenticateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserPreferencesResponseDto getUserPreferences(Authentication authentication) {
        return userPreferencesService.getUserPreferences(authenticateService.getUserByAuthenticatedIdentity(authentication).getPublicId());
    }

    @GetMapping("/app")
    @ResponseStatus(HttpStatus.OK)
    public UserPreferencesAppBehaviourDto getUserAppBehaviourPreferences(Authentication authentication) {
        return userPreferencesService.getUserAppBehaviourPreferences(authenticateService.requireAuthenticatedUserPublicId(authentication));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserPreferences(@Valid @RequestBody UpdateUserPreferencesRequestDto request, Authentication authentication) {
        if (request.isEmpty()) throw new BadRequestException("At least one field must be provided.");

        userPreferencesService.updateUserPreferences(
                request,
                authenticateService.requireAuthenticatedUserPublicId(authentication)
        );
    }
}
