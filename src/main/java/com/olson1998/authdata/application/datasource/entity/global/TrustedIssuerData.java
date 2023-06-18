package com.olson1998.authdata.application.datasource.entity.global;

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
@Table(name = "TNTTRUSTISS")
public class TrustedIssuerData implements Persistable<String> {

    @Id
    @Column(name = "TNTTRUSTISS", nullable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "TNTID", nullable = false, updatable = false)
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
