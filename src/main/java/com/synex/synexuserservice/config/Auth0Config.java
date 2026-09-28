package com.synex.synexuserservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Auth0Config {

    @Bean
    public RestClient auth0RestClient(@Value("${auth0.domain}") String auth0Domain) {
        return RestClient.builder().baseUrl(auth0Domain).build();
    }

    @Bean
    public RestClient auth0ManagementRestClient(@Value("${auth0.domain}") String auth0Domain) {
        return RestClient.builder().baseUrl(auth0Domain).build();
    }
}
