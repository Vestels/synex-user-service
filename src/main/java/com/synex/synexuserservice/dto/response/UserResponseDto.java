package com.synex.synexuserservice.dto.response;

import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.enums.UserStatus;

import java.time.Instant;

public record UserResponseDto(
        String email,
        UserStatus userStatus,
        Instant createdAt,
        Instant updatedAt,
        Instant lastLoginAt,
        Instant lastActivityAt,
        Instant deletionRequestAt,
        Instant scheduledDeletionAt
) {

    public static UserResponseDto from(UserEntity user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getLastLoginAt(),
                user.getLastActivityAt(),
                user.getDeletionRequestAt(),
                user.getScheduledDeletionAt()
        );
    }
}
