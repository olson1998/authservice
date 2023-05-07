package com.olson1998.authservice.application.datasource.properties;

import java.util.Properties;

public interface LocalJpaProperties {

    void setHbmDdl(String ddl);

    Properties toSpringJpaProperties();
}
