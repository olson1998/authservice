package com.olson1998.authdata.application.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

@Import(OAuth2AuthorizationServerConfiguration.class)

@Configuration
public class OAuthConfig {

    public RegisteredClientRepository registeredClientRepository(){

    }
}
