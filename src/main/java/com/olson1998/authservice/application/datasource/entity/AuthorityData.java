package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Table(name = "ROLEAUTHORITY")
@SequenceGenerator(name = "AUTHORITY_ID_SEQ", sequenceName = "AUTHORITY_ID_SEQ", allocationSize = 1)

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityData implements Authority {

    @Id
    @Column(name = "AUTHORITYID")
    private String id;

    @Column(name = "AUTHORITYNM", nullable = false)
    private String authorityName;

    @Column(name = "AUTHORITYTOKEN")
    private String authorityToken;

    @Column(name = "AUTHORITYLVL")
    private Integer level;

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
    public Integer getLevel() {
        return level;
    }

    @Override
    public Long getExpiringTime() {
        return expiringTime;
    }

    @Override
    public String getAuthorityToken() {
        return authorityToken;
    }

    @PrePersist
    public void generateId(){
        this.id = new StringBuilder("AUTH&")
                .append(RandomStringUtils.randomAlphanumeric(12).toUpperCase())
                .toString();
    }

    public AuthorityData(@NonNull AuthorityDetails authorityDetails) {
        this.authorityName = authorityDetails.getName();
        this.authorityToken = authorityDetails.getToken();
        this.level = authorityDetails.getLevel();
        this.expiringTime = authorityDetails.getExpiringTime();
    }
}
