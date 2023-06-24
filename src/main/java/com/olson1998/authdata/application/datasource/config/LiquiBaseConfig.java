package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.datasource.properties.DataSourceChangelogProps;
import com.olson1998.authdata.application.security.config.LocalServiceInstanceSign;
import liquibase.integration.spring.SpringLiquibase;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class LiquiBaseConfig {

    @Bean
    public SpringLiquibase springLiquibase(@NonNull DataSourceChangelogProps dataSourceChangelogProps,
                                           @Qualifier("globalDataSource") DataSource globalDataSource,
                                           @NonNull LocalServiceInstanceSign localServiceInstanceSign){
        var liquiParams = new HashMap<String, String>();
        var springLiquibaseBean = new SpringLiquibase();
        var globalChangeLog = dataSourceChangelogProps.getGlobalDataBase().getChangeLog();
        liquiParams.put("service.ip", localServiceInstanceSign.getValue());
        springLiquibaseBean.setShouldRun(true);
        springLiquibaseBean.setChangeLogParameters(liquiParams);
        springLiquibaseBean.setChangeLog(globalChangeLog);
        springLiquibaseBean.setDataSource(globalDataSource);
        return springLiquibaseBean;
    }
}
