package com.olson1998.authservice.application.datasource.entity.main;

import com.olson1998.authservice.domain.port.data.entity.SqlDataSourceEntityMapping;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHSVENT")
@NoArgsConstructor
@AllArgsConstructor
public class SqlDataSourceEntityMappingData implements SqlDataSourceEntityMapping {

    @Id
    @Column(name = "MAPID", nullable = false)
    private String id;

    @Column(name = "MAPTNT", nullable = false)
    private String tenantId;

    @Column(name = "MAPDBID", nullable = false)
    private String databaseId;

    @Column(name = "MAPDBSCH")
    private String schema = null;

    @Column(name = "MAPENT", nullable = false)
    private String entityClassName;

    public String getTenantId() {
        return tenantId;
    }

    @Override
    public String getDatabaseId() {
        return databaseId;
    }

    @Override
    public String getEntityClassName() {
        return entityClassName;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    public SqlDataSourceEntityMappingData(String id, String tenantId, String databaseId, String schema, Class<?> entityClass) {
        this.id = id;
        this.tenantId = tenantId;
        this.databaseId = databaseId;
        this.schema = schema;
        this.entityClassName = entityClass.getName();
    }
}
