package com.synex.synexuserservice.dto.response;

import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;

import java.time.Instant;

public record UserIdentityResponseDto(
        IdentityProvider provider,
        Instant createdAt,
        Instant lastUsedAt
) {

    public static UserIdentityResponseDto from(UserIdentityEntity identity) {
        return new UserIdentityResponseDto(
                identity.getProvider(),
                identity.getCreatedAt(),
                identity.getLastUsedAt()
        );
    }
}
