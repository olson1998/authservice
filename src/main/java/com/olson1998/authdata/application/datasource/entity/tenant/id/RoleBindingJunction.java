package com.olson1998.authdata.application.datasource.entity.tenant.id;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import lombok.*;

import java.io.Serializable;


@Embeddable

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingJunction implements Serializable {

    @Column(name = "ROLEID", nullable = false, updatable = false)
    private String roleId;

    @Column(name = "AUTHORITYID", nullable = false, updatable = false)
    private String authorityId;

    public RoleBindingJunction(@NonNull RoleBindingClaim claim) {
        this.roleId=claim.getRoleId();
        this.authorityId=claim.getAuthorityId();
    }
}
