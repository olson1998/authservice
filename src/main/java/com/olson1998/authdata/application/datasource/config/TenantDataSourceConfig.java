package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.datasource.LocalThreadTenantDataSource;
import com.olson1998.authdata.application.datasource.properties.JpaDialects;
import com.olson1998.authdata.application.datasource.properties.TenantDataSourceJpaProperties;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(
        basePackages = "com.olson1998.authdata.application.datasource.repository.tenant.spring",
        entityManagerFactoryRef = "tenantDatasourceEntityManager",
        transactionManagerRef = "tenantDatasourceTransactionManager"
)

@Configuration
public class TenantDataSourceConfig {

    @Bean
    public HibernateJpaVendorAdapter tenantHibernateJpaVendorAdapter(){
        var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setPrepareConnection(false);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean tenantDatasourceEntityManager(LocalThreadTenantDataSource localThreadTenantDataSource,
                                                                                TenantDataSourceJpaProperties tenantDataSourceJpaProperties,
                                                                                @Qualifier("tenantHibernateJpaVendorAdapter") HibernateJpaVendorAdapter hibernateJpaVendorAdapter,
                                                                                @NonNull JpaDialects jpaDialects){
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setPersistenceUnitName(tenantDataSourceJpaProperties.getPersistenceUnitName());
        entityManager.setDataSource(localThreadTenantDataSource);
        entityManager.setPackagesToScan(
                "com.olson1998.authdata.application.datasource.entity.tenant",
                "com.olson1998.authdata.application.datasource.repository.tenant.spring"
        );
        entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        switch (tenantDataSourceJpaProperties.getDialect()){
            case HIBERNATE -> entityManager.setJpaDialect(jpaDialects.getHibernateJpaDialect());
            case DEFAULT -> entityManager.setJpaDialect(jpaDialects.getDefaultJpaDialect());
            case ECLIPSE -> entityManager.setJpaDialect(jpaDialects.getEclipseLinkJpaDialect());
            default -> throw new IllegalArgumentException("jpa dialect unknown");
        }
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager tenantDatasourceTransactionManager(@Qualifier("tenantDatasourceEntityManager") LocalContainerEntityManagerFactoryBean em){
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(em.getObject());
        return transactionManager;
    }
}
