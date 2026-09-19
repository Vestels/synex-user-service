package com.synex.synexuserservice.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Auth0IdentityDto(
        String provider,
        @JsonProperty("user_id") String subject,
        String connection
) {
}
