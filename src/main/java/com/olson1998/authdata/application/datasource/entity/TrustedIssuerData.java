package com.olson1998.authdata.application.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

@Entity
@Table(name = "TRTNTISS")
public class TrustedIssuerData {

    @Id
    @Column(name = "TRTNTISSNM")
    private String name;

    @Column(name = "TRTNTISSTNTID", nullable = false)
    private String tenantId;
}
