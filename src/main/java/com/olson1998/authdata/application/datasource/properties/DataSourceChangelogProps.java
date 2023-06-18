package com.olson1998.authdata.application.datasource.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.datasource.tenant.liquidbase")
public class DataSourceChangelogProps {

    private ChangeLog tenantDataBase = new ChangeLog("classpath:/changelog/tenant_database_changelog_.xml");

    private ChangeLog globalDataBase = new ChangeLog("classpath:/changelog/global_database_changelog_.xml");

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ChangeLog{

        private String changeLog;
    }
}
