package com.olson1998.authdata.application.datasource.entity.global.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable

@Getter
@Setter
@EqualsAndHashCode

@NoArgsConstructor
@AllArgsConstructor
public class TrustedIssuerId implements Serializable {

    private static final long serialVersionUID = 8640815750639751780L;

    @Column(name = "TNTTRUSTISS", nullable = false, updatable = false, unique = true)
    private String name;

    @Column(name = "TNTID", nullable = false, updatable = false)
    private String tenantId;
}
