package com.olson1998.authdata.application.datasource.entity.id;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingJunction implements Serializable {

    @Column(name = "ROLEID", nullable = false)
    private String roleId;

    @Column(name = "AUTHORITYID", nullable = false)
    private String authorityId;

    public RoleBindingJunction(@NonNull RoleBindingClaim claim) {
        this.roleId=claim.getRoleId();
        this.authorityId=claim.getAuthorityId();
    }
}
