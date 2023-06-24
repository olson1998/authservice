package com.olson1998.authdata.application.datasource.entity.global;

import com.olson1998.authdata.application.datasource.entity.global.id.TenantDataSourceUserId;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "TNTNDSUSR")

@NoArgsConstructor
@AllArgsConstructor
public class TenantDataSourceUserData implements TenantDataSourceUser {

    @EmbeddedId
    private TenantDataSourceUserId userId;

    @Column(name = "TNTDSUSRSEC", nullable = false)
    private String password;

    @Override
    public Long getDataSourceId() {
        return userId.getDataSourceId();
    }

    @Override
    public String getUsername() {
        return userId.getUsername();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isMatching(@NonNull TenantDataSourceUser tenantDataSourceUser) {
        return this.userId.getDataSourceId().equals(tenantDataSourceUser.getDataSourceId()) &&
                this.userId.getUsername().equals(tenantDataSourceUser.getUsername()) &&
                this.password.equals(tenantDataSourceUser.getPassword());
    }

    public TenantDataSourceUserData(Long dataSourceId, String username, String password) {
        this.password = password;
        this.userId = new TenantDataSourceUserId(dataSourceId, username);
    }
}
