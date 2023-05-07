package com.olson1998.authservice.application.datasource.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.olson1998.authservice.application.datasource.properties.MainDatasourceJpaProperties;
import com.olson1998.authservice.application.datasource.properties.MainDatasourceProperties;
import com.olson1998.authservice.application.datasource.properties.MainDatasourceSslProperties;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Setter

@EnableJpaRepositories

@Configuration
public class MainDataSourceConfig {

    @Bean
    public DataSource mainDataSource(@NonNull MainDatasourceProperties datasourceProperties,
                                     @NonNull MainDatasourceSslProperties sslProperties){
        var mainDataSource = new SQLServerDataSource();
        mainDataSource.setServerName(datasourceProperties.getUrl());
        mainDataSource.setPortNumber(datasourceProperties.getPort());
        mainDataSource.setDatabaseName(datasourceProperties.getDatabase());
        mainDataSource.setDescription(datasourceProperties.getDescription());
        mainDataSource.setTrustServerCertificate(datasourceProperties.isTrustCertificate());
        mainDataSource.setUser(datasourceProperties.getUser());
        mainDataSource.setPassword(datasourceProperties.getEncryptedPassword());
        mainDataSource.setLoginTimeout(datasourceProperties.getLoginTimeout());
        if(sslProperties.isEnabled()){
            mainDataSource.setKeyStoreLocation(sslProperties.getKeystoreLocation());
            mainDataSource.setKeyStorePrincipalId(sslProperties.getKeystorePrincipal());
            mainDataSource.setKeyStoreSecret(sslProperties.getKeystoreSecret());
            mainDataSource.setTrustStoreType(sslProperties.getTruststoreType());
            mainDataSource.setTrustStore(sslProperties.getTruststoreLocation());
            mainDataSource.setTrustStorePassword(sslProperties.getTruststorePassword());
        }
        return mainDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mainDatasourceEntityManager(@Qualifier("mainDataSource") DataSource mainDataSource,
                                                                              @NonNull MainDatasourceJpaProperties mainDatasourceJpaProperties,
                                                                              @NonNull JpaDialects jpaDialects){
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(mainDataSource);
        entityManager.setPersistenceUnitName(mainDatasourceJpaProperties.getPersistenceUnitName());
        entityManager.setJpaProperties(mainDatasourceJpaProperties.toSpringJpaProperties());
        switch (mainDatasourceJpaProperties.getDialect()){
            case HIBERNATE -> entityManager.setJpaDialect(jpaDialects.getHibernateJpaDialect());
            case DEFAULT -> entityManager.setJpaDialect(jpaDialects.getDefaultJpaDialect());
            case ECLIPSE -> entityManager.setJpaDialect(jpaDialects.getEclipseLinkJpaDialect());
            default -> throw new IllegalArgumentException("jpa dialect unknown");
        }
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager mainDatasourceTransactionManager(@Qualifier("mainDatasourceEntityManager")
                                                                           @NonNull LocalContainerEntityManagerFactoryBean em){
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(em.getObject());
        return transactionManager;
    }
}
