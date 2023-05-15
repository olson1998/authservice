package com.olson1998.authservice.application.datasource.entity;

import com.olson1998.authservice.application.datasource.entity.id.UserMembershipJunction;
import com.olson1998.authservice.domain.port.request.data.UserMembershipClaim;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHUSERMEM")
@IdClass(UserMembershipJunction.class)

@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipData {

    @EmbeddedId
    private UserMembershipJunction junction;

    public UserMembershipData(UserMembershipClaim claim) {
        this.junction = new UserMembershipJunction(claim);
    }
}
