package com.olson1998.authservice.domain.port.data.entity;

public interface SqlDataSourceEntityMapping {

    String getTenantId();

    String getDatabaseId();

    String getEntityClassName();

    String getSchema();

}
