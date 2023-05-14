package com.olson1998.authservice.application.datasource.entity.id;

import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Embeddable

@NoArgsConstructor
@AllArgsConstructor
public class UserMembershipJunction implements Serializable {

    @Column(name = "AUTHUSERID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "MEMCONO", nullable = false, updatable = false)
    private Long companyNumber;

    @Column(name = "MEMREGID", nullable = false, updatable = false)
    private String regionId;

    @Column(name = "MEMGRPID", nullable = false)
    private Long groupId;

    @Column(name = "MEMTMID", nullable = false)
    private Long teamId;

    public UserMembershipJunction(@NonNull UserMembershipClaim claim) {
        this.userId = claim.getUserId();
        this.companyNumber = claim.getCompanyNumber();
        this.regionId = claim.getRegionId();
        this.groupId = claim.getGroupId();
        this.teamId = claim.getTeamId();
    }
}
