package com.olson1998.authdata.application.datasource.entity.global.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class TenantDataSourceUserId implements Serializable {

    @Serial
    private static final long serialVersionUID = 6162890169884477239L;

    @Column(name = "TNTDSID")
    private Long dataSourceId;

    @Column(name = "TNTUSRNM", length = 30)
    private String username;
}
