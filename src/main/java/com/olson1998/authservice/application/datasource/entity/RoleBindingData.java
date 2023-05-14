package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.id.RoleBindingJunction;
import com.olson1998.authservice.domain.port.request.entity.RoleBindingClaim;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHROLEBIND")
@IdClass(RoleBindingJunction.class)

@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingData {

    @EmbeddedId
    private RoleBindingJunction junction;

    public RoleBindingData(RoleBindingClaim claim) {
        this.junction=new RoleBindingJunction(claim);
    }
}
