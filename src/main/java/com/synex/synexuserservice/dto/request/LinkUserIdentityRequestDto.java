package com.synex.synexuserservice.dto.request;

import com.synex.synexuserservice.enums.IdentityProvider;
import lombok.NonNull;

public record LinkUserIdentityRequestDto(
        @NonNull
        String idToken,
        IdentityProvider provider
) {
}
