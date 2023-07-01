package com.olson1998.authdata.application.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.application.datasource.discovery")
public class TenantDataSourceDiscoveryProps {

    private boolean enabled = true;

    private String[] tenants = new String[0];

}
