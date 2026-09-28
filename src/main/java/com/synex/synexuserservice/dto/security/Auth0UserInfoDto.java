package com.synex.synexuserservice.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0UserInfoDto(
        String sub,
        String nickname,
        String name,
        String picture,
        @JsonProperty("updated_at")
        String updatedAt,
        String email,
        @JsonProperty("email_verified")
        Boolean emailVerified
) {
}
