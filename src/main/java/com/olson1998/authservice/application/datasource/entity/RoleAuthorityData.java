package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROLEAUTHORITY")
@SequenceGenerator(name = "AUTHORITY_ID_SEQ", sequenceName = "AUTHORITY_ID_SEQ", allocationSize = 1)

@NoArgsConstructor
@AllArgsConstructor
public class RoleAuthorityData implements RoleAuthority {

    @Id
    @Column(name = "AUTHORITYID")
    private String authorityId;

    @Column(name = "AUTHORITYNM", nullable = false)
    private String authorityName;

    @Column(name = "AUTHORITYTOKEN")
    private String authorityToken;

    @Column(name = "AUTHORITYEXP")
    private Long expiringTime;

    @Override
    public String getAuthorityId() {
        return authorityId;
    }

    @Override
    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public String getAuthorityToken() {
        return authorityToken;
    }
}
