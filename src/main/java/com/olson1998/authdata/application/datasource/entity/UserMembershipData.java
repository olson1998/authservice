package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.id.UserMembershipJunction;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHUSERMEM")

@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipData implements UserMembership {

    @EmbeddedId
    private UserMembershipJunction junction;

    public UserMembershipData(Long userId, UserMembershipClaim claim) {
        this.junction = new UserMembershipJunction(userId, claim);
    }

    @Override
    public Long getUserId() {
        return junction.getUserId();
    }

    @Override
    public Long getCompanyNumber() {
        return junction.getCompanyNumber();
    }

    @Override
    public String getRegionId() {
        return junction.getRegionId();
    }

    @Override
    public Long getGroupId() {
        return junction.getGroupId();
    }

    @Override
    public Long getTeamId() {
        return junction.getTeamId();
    }

}
