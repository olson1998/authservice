package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.id.RoleBindingJunction;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "AUTHROLEBIND")

@NoArgsConstructor
@AllArgsConstructor
public class RoleBindingData implements Persistable<RoleBindingJunction>, RoleBinding {

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

    @Override
    public RoleBindingJunction getId() {
        return junction;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
