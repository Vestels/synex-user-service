package com.synex.synexuserservice.enums;

import lombok.Getter;

@Getter
public enum IdentityProvider {
    PASSWORD("auth0"),
    GOOGLE("google-oauth2");

    private final String name;

    IdentityProvider(String name) {
        this.name = name;
    }

    public static IdentityProvider fromName(String name) {
        for (IdentityProvider provider : values()) {
            if (provider.name.equals(name)) {
                return provider;
            }
        }

        throw new IllegalArgumentException(
                "Unknown identity provider: " + name
        );
    }
}
