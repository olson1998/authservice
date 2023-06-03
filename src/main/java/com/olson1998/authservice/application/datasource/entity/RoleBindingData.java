package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.id.RoleBindingJunction;
import com.olson1998.authservice.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

import static java.util.Map.entry;

@Entity
@Table(name = "AUTHROLEBIND")
@IdClass(RoleBindingJunction.class)

@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingData implements RoleBinding {

    @EmbeddedId
    private RoleBindingJunction junction;

    public RoleBindingData(RoleBindingClaim claim) {
        this.junction=new RoleBindingJunction(claim);
    }

    @Override
    public String getRoleId() {
        return junction.getRoleId();
    }

    @Override
    public String getAuthorityId() {
        return junction.getAuthorityId();
    }

}
