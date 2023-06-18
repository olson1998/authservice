package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.application.datasource.entity.tenant.id.UserMembershipJunction;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Entity
@Table(name = "AUTHUSERMEM")

@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipData implements Persistable<String>, UserMembership {

    @Id
    @Column(name = "USERMEMID")
    private String id;

    private UserMembershipJunction junction;

    public UserMembershipData(Long userId, UserMembershipClaim claim) {
        this.junction = new UserMembershipJunction(userId, claim);
        this.id = new StringBuilder("USER&")
                .append(userId)
                .append('&')
                .append(randomAlphanumeric(6).toUpperCase())
                .toString();
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
