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
public class AuthorityData implements RoleAuthority {

    @Id
    @Column(name = "AUTHORITYID")
    private String id;

    @Column(name = "AUTHORITYNM", nullable = false)
    private String authorityName;

    @Column(name = "AUTHORITYTOKEN")
    private String authorityToken;

    @Column(name = "AUTHORITYEXP")
    private Long expiringTime;

    @Override
    public String getId() {
        return id;
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
