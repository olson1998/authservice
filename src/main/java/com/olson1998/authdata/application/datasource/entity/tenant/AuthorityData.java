package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "ROLEAUTHORITY")

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityData implements Persistable<String>, Authority {

    @Id
    @Column(name = "AUTHORITYID")
    private String id;

    @Column(name = "AUTHORITYNM", nullable = false, updatable = false)
    private String authorityName;

    @Column(name = "AUTHORITYTOKEN", updatable = false)
    private String authorityToken;

    @Column(name = "AUTHORITYLVL", updatable = false)
    private Integer level;

    @Column(name = "AUTHORITYEXP", updatable = false)
    private Long expiringTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
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
