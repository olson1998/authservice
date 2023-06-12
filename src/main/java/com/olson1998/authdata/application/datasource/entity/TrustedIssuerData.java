package com.olson1998.authdata.application.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TRTNTISS")
public class TrustedIssuerData implements Persistable<String> {

    @Id
    @Column(name = "TRTNTISSNM", nullable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "TRTNTISSTNTID", nullable = false, updatable = false)
    private String tenantId;

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
