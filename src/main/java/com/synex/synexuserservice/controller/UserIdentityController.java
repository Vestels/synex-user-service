package com.synex.synexuserservice.controller;

import com.synex.synexuserservice.dto.response.UserIdentityResponseDto;
import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.service.UserIdentityService;
import com.synex.synexuserservice.service.support.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/identities")
public class UserIdentityController {

    private final UserIdentityService userIdentityService;
    private final AuthenticateService authenticateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserIdentityResponseDto> getAllUserIdentities(Authentication authentication) {
        UserEntity user = authenticateService.getUserByAuthenticatedIdentity(authentication);

        return userIdentityService.getAllUserIdentities(user.getPublicId());
    }
}
