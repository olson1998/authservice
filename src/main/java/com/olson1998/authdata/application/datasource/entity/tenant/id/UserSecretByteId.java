package com.olson1998.authdata.application.datasource.entity.tenant.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable

@Getter
@Setter
@EqualsAndHashCode

@NoArgsConstructor
@AllArgsConstructor
public class UserSecretByteId implements Serializable {

    @Serial
    private static final long serialVersionUID = 8904386628601844520L;

    @Column(name = "USERID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "USERSECBYTEORDINAL", nullable = false, updatable = false)
    private Integer byteOrdinal;
}
