package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.datasource.properties.TenantDataSourceChangelogProps;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquiBaseConfig {

    @Bean
    public SpringLiquibase springLiquibase(@NonNull TenantDataSourceChangelogProps tenantDataSourceChangelogProps){
        var springLiquibaseBean = new SpringLiquibase();
        springLiquibaseBean.setShouldRun(false);
        springLiquibaseBean.setChangeLog(tenantDataSourceChangelogProps.getChangeLog());
        return springLiquibaseBean;
    }
}
