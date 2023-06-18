package com.olson1998.authdata.application.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.Properties;

import static com.olson1998.authdata.application.datasource.properties.JpaDialectInstance.HIBERNATE;

@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.datasource.tenant.jpa")
public class TenantDataSourceJpaProperties implements LocalJpaProperties{

    @Getter
    private String persistenceUnitName = "tenant";

    @Getter
    private JpaDialectInstance dialect = HIBERNATE;

    private String hbm2Ddl;
    @Override
    public Properties toSpringJpaProperties() {
        var jpaProps = new Properties();
        Optional.ofNullable(hbm2Ddl).ifPresent(ddl -> jpaProps.put("hibernate.hbm2ddl.auto", ddl));
        return jpaProps;
    }
}
