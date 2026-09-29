package com.synex.synexuserservice.controller;

import com.synex.synexuserservice.dto.response.UserResponseDto;
import com.synex.synexuserservice.service.UserService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import com.synex.synexuserservice.service.support.UserProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {

    private final UserService userService;
    private final AuthenticateService authenticateService;
    private final UserProvisioningService userProvisioningService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getOrProvisionUser(Authentication authentication) {
        return userProvisioningService.getOrProvisionUser(authentication);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void setUserScheduledDeletion(Authentication authentication) {
        userService.setUserScheduledDeletion(authenticateService.requireAuthenticatedUserPublicId(authentication));
    }

    @PostMapping("/deletion/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void clearUserScheduledDeletion(Authentication authentication) {
        userService.clearUserScheduledDeletion(authenticateService.requireAuthenticatedUserPublicId(authentication));
    }
}
