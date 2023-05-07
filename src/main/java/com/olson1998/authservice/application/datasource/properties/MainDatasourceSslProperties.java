package com.olson1998.authservice.application.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.KeyStore;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "olson1998.authservice.datasource.main.ssl")
public class MainDatasourceSslProperties {

    private boolean enabled = false;

    private String keystoreLocation = null;

    private String keystorePrincipal = null;

    private String keystoreSecret = null;

    private String truststoreLocation = null;

    private String truststoreUser = null;

    private String truststorePassword = null;

    private String truststoreType = "JKS";
}
