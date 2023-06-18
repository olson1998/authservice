package com.olson1998.authdata.application.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.datasource.tenant.liquidbase")
public class TenantDataSourceChangelogProps {

    private String changeLog = "classpath:/changelog/tenant_database_changelog_.xml";
}
