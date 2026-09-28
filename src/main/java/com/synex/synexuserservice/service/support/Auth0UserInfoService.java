package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.dto.security.Auth0UserResponseDto;
import com.synex.synexuserservice.dto.security.Auth0UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class Auth0UserInfoService {

    private final RestClient auth0RestClient;
    private final AuthenticateService authenticateService;

    public Auth0UserInfoDto getUserInfo(String accessToken) {
        return auth0RestClient
                .get()
                .uri("/userinfo")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(Auth0UserInfoDto.class);
    }

    public Auth0UserResponseDto getUserInfoData(Authentication authentication) {
        return Auth0UserResponseDto.from(getUserInfo(authenticateService.getJwtTokenFromPrincipal(authentication).getTokenValue()));
    }
}
