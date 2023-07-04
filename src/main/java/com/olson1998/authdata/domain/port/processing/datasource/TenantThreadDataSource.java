package com.olson1998.authdata.domain.port.processing.datasource;

public interface TenantThreadDataSource {

    void setCurrentForTenant(String tid);

    void clean();
}
