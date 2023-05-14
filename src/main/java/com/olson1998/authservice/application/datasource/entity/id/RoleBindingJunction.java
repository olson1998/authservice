package com.olson1998.authservice.application.datasource.entity.id;

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
}
