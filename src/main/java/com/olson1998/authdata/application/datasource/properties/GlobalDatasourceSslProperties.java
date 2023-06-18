package com.olson1998.authdata.application.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "olson1998.authdata.datasource.main.ssl")
public class GlobalDatasourceSslProperties {

    private boolean enabled = false;

    private String keystoreLocation = null;

    private String keystorePrincipal = null;

    private String keystoreSecret = null;

    private String truststoreLocation = null;

    private String truststoreUser = null;

    private String truststorePassword = null;

    private String truststoreType = "JKS";
}
