package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.datasource.properties.GlobalDatasourceJpaProperties;
import com.olson1998.authdata.application.datasource.properties.GlobalDatasourceProperties;
import com.olson1998.authdata.application.datasource.properties.JpaDialects;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;


@EnableJpaRepositories(
        basePackages = "com.olson1998.authdata.application.datasource.repository.global.spring",
        entityManagerFactoryRef = "globalDatasourceEntityManager",
        transactionManagerRef = "globalDatasourceTransactionManager"
)

@Configuration
public class GlobalDataSourceConfig {

    @Bean
    public DataSource globalDataSource(@NonNull GlobalDatasourceProperties datasourceProperties,
                                       @NonNull SqlDataSourceFactory sqlDataSourceFactory) {
        return sqlDataSourceFactory.fabricate(datasourceProperties);
    }

    @Bean
    public HibernateJpaVendorAdapter globalDatasourceHibernateJpaVendorAdapter(){
        var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean globalDatasourceEntityManager(@Qualifier("globalDataSource") DataSource mainDataSource,
                                                                                GlobalDatasourceJpaProperties globalDatasourceJpaProperties,
                                                                                @Qualifier("globalDatasourceHibernateJpaVendorAdapter") HibernateJpaVendorAdapter hibernateJpaVendorAdapter,
                                                                                @NonNull JpaDialects jpaDialects){
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setPersistenceUnitName(globalDatasourceJpaProperties.getPersistenceUnitName());
        entityManager.setDataSource(mainDataSource);
        entityManager.setPackagesToScan(
                "com.olson1998.authdata.application.datasource.entity.global",
                "com.olson1998.authdata.application.datasource.repository.global.spring"
        );
        entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        switch (globalDatasourceJpaProperties.getDialect()){
            case HIBERNATE -> entityManager.setJpaDialect(jpaDialects.getHibernateJpaDialect());
            case DEFAULT -> entityManager.setJpaDialect(jpaDialects.getDefaultJpaDialect());
            case ECLIPSE -> entityManager.setJpaDialect(jpaDialects.getEclipseLinkJpaDialect());
            default -> throw new IllegalArgumentException("jpa dialect unknown");
        }
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager globalDatasourceTransactionManager(@Qualifier("globalDatasourceEntityManager") LocalContainerEntityManagerFactoryBean em){
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(em.getObject());
        return transactionManager;
    }
}
