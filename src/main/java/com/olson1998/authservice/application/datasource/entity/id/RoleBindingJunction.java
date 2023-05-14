package com.olson1998.authservice.application.datasource.entity.id;

import com.olson1998.authservice.domain.port.request.entity.RoleBindingClaim;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter

@Embeddable

@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingJunction implements Serializable {

    @Column(name = "ROLEID")
    private String roleId;

    @Column(name = "AUTHORITYID")
    private String authorityId;

    public RoleBindingJunction(@NonNull RoleBindingClaim claim) {
        this.roleId=claim.getRoleId();
        this.authorityId=claim.getAuthorityId();
    }
}
