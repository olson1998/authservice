package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.id.TenantDataSourceUserId;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "TNTNDSUSR")

@NoArgsConstructor
public class TenantDataSourceUserData implements TenantDataSourceUser {

    @EmbeddedId
    private TenantDataSourceUserId tenantDataSourceUserId;

    @Column(name = "TNTDSUSRSEC", nullable = false)
    private String password;

    @Override
    public String getTid() {
        return tenantDataSourceUserId.getTid();
    }

    @Override
    public String getUsername() {
        return tenantDataSourceUserId.getUsername();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isMatching(@NonNull TenantDataSourceUser tenantDataSourceUser) {
        return this.tenantDataSourceUserId.getTid().equals(tenantDataSourceUser.getTid()) &&
                this.tenantDataSourceUserId.getUsername().equals(tenantDataSourceUser.getUsername()) &&
                this.password.equals(tenantDataSourceUser.getPassword());
    }


}
