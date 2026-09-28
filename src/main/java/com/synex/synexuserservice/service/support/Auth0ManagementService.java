package com.synex.synexuserservice.service.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synex.synexuserservice.config.Auth0ManagementProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class Auth0ManagementService {

    private final RestClient auth0ManagementRestClient;
    private final Auth0ManagementProperties properties;

    public Auth0ManagementService(
            @Qualifier("auth0ManagementRestClient")
            RestClient auth0ManagementRestClient,
            Auth0ManagementProperties properties
    ) {
        this.auth0ManagementRestClient = auth0ManagementRestClient;
        this.properties = properties;
    }

    public boolean userExists(String auth0UserId) {
        try {
            auth0ManagementRestClient
                    .get()
                    .uri("/api/v2/users/{userId}", auth0UserId)
                    .headers(headers -> headers.setBearerAuth(getManagementToken()))
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (HttpClientErrorException.NotFound exception) {
            return false;
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

    private String getManagementToken() {
        return Objects.requireNonNull(auth0ManagementRestClient
                        .post()
                        .uri("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(
                                "grant_type=client_credentials" +
                                        "&client_id=" + properties.getClientId() +
                                        "&client_secret=" + properties.getClientSecret() +
                                        "&audience=" + properties.getAudience()
                        )
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