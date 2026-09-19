package com.synex.synexuserservice.controller;

import com.synex.synexuserservice.dto.request.UpdateUserProfileRequestDto;
import com.synex.synexuserservice.dto.response.UserProfileResponseDto;
import com.synex.synexuserservice.exception.BadRequestException;
import com.synex.synexuserservice.service.UserProfileService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final AuthenticateService authenticateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponseDto getUserProfile(Authentication authentication) {
        return userProfileService.getUserProfile(
                authenticateService.getUserByAuthenticatedIdentity(authentication).getPublicId()
        );
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUserProfile(@Valid @RequestBody UpdateUserProfileRequestDto request, Authentication authentication) {
        if (request.isEmpty()) throw new BadRequestException("At least one field must be provided.");

        userProfileService.updateUserprofile(
                request,
                authenticateService.requireAuthenticatedUserPublicId(authentication)
        );
    }
}
