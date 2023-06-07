package com.olson1998.authdata.application.datasource.properties;

import java.util.Properties;

public interface LocalJpaProperties {

    void setHbmDdl(String ddl);

    Properties toSpringJpaProperties();
}
