package com.synex.synexuserservice.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Auth0UserResponseDto(
        @JsonProperty("user_id") String subject,
        String email,
        List<Auth0IdentityDto> identities
) {
}
