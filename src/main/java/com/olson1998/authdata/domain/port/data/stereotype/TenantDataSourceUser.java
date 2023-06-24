package com.olson1998.authdata.domain.port.data.stereotype;

public interface TenantDataSourceUser {

    Long getDataSourceId();

    String getUsername();

    String getPassword();

    boolean isMatching(TenantDataSourceUser tenantDataSourceUser);
}
