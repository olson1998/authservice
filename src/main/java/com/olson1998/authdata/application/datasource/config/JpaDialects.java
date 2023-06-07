package com.olson1998.authdata.application.datasource.config;

import lombok.Getter;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JpaDialects {

    private final HibernateJpaDialect hibernateJpaDialect= new HibernateJpaDialect();

    private final EclipseLinkJpaDialect eclipseLinkJpaDialect = new EclipseLinkJpaDialect();

    private final DefaultJpaDialect defaultJpaDialect = new DefaultJpaDialect();

}
