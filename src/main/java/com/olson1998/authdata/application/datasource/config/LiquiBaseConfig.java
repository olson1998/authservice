package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.datasource.properties.DataSourceChangelogProps;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquiBaseConfig {

    @Bean
    public SpringLiquibase springLiquibase(@NonNull DataSourceChangelogProps dataSourceChangelogProps,
                                           @Qualifier("globalDataSource") DataSource globalDataSource){
        var springLiquibaseBean = new SpringLiquibase();
        var globalChangeLog = dataSourceChangelogProps.getGlobalDataBase().getChangeLog();
        springLiquibaseBean.setShouldRun(true);
        springLiquibaseBean.setChangeLog(globalChangeLog);
        springLiquibaseBean.setDataSource(globalDataSource);
        return springLiquibaseBean;
    }
}
