package com.synex.synexuserservice.service.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synex.synexuserservice.config.Auth0ManagementProperties;
import com.synex.synexuserservice.dto.security.Auth0UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Auth0ManagementService {

    private final RestClient auth0ManagementRestClient;
    private final Auth0ManagementProperties properties;

    public Optional<Auth0UserResponseDto> getUser(String auth0UserId) {
        try {
            Auth0UserResponseDto user = auth0ManagementRestClient
                    .get()
                    .uri("/api/v2/users/{userId}", auth0UserId)
                    .headers(headers -> headers.setBearerAuth(getManagementToken()))
                    .retrieve()
                    .body(Auth0UserResponseDto.class);

            return Optional.ofNullable(user);

        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void deleteUser(String auth0UserId) {
        auth0ManagementRestClient
                .delete()
                .uri("/api/v2/users/{userId}", auth0UserId)
                .headers(headers -> headers.setBearerAuth(getManagementToken()))
                .retrieve()
                .toBodilessEntity();
    }

    public void linkAccount(String auth0UserId, String provider, String secondaryUserId) {
        auth0ManagementRestClient
                .post()
                .uri("/api/v2/users/{userId}/identities", auth0UserId)
                .headers(headers -> headers.setBearerAuth(getManagementToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "provider", provider,
                        "user_id", secondaryUserId
                ))
                .retrieve()
                .toBodilessEntity();
    }

    public void deleteLinkedAccount(String auth0UserId, String provider, String secondaryUserId) {
        auth0ManagementRestClient
                .delete()
                .uri("/api/v2/users/{userId}/identities/{provider}/{secondaryUserId}", auth0UserId, provider, secondaryUserId)
                .headers(headers -> headers.setBearerAuth(getManagementToken()))
                .retrieve()
                .toBodilessEntity();
    }

    private String getManagementToken() {
        return Objects.requireNonNull(auth0ManagementRestClient
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(new LinkedMultiValueMap<>(Map.of(
                        "grant_type", List.of("client_credentials"),
                        "client_id", List.of(properties.getClientId()),
                        "client_secret", List.of(properties.getClientSecret()),
                        "audience", List.of(properties.getAudience())
                )))
                .retrieve()
                .body(Auth0TokenResponse.class)
        ).accessToken();
    }

    private record Auth0TokenResponse(
            @JsonProperty("access_token")
            String accessToken
    ) {
    }
}