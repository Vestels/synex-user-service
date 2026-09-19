package com.synex.synexuserservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth0.management")
public class Auth0ManagementProperties {

    private String clientId;
    private String clientSecret;
    private String audience;
}