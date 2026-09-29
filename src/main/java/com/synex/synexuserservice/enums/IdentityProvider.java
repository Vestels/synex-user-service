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
}
