package com.olson1998.authdata.application.datasource.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Embeddable
public class TenantDataSourceUserId implements Serializable {

    @Serial
    private static final long serialVersionUID = 6162890169884477239L;

    @Column(name = "TNTID")
    private String tid;

    @Column(name = "TNTUSRNM")
    private String username;
}
