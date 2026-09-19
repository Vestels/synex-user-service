package com.synex.synexuserservice.dto.request;

import lombok.NonNull;

public record DeleteLinkedUserIdentityRequestDto(
        @NonNull
        String provider
) {
}
