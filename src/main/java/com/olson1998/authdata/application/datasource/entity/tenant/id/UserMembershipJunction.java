package com.olson1998.authdata.application.datasource.entity.tenant.id;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
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
public class UserMembershipJunction implements Serializable {

    @Column(name = "USERID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "MEMCONO", updatable = false)
    private Long companyNumber;

    @Column(name = "MEMREGID", updatable = false)
    private String regionId;

    @Column(name = "MEMGRPID", updatable = false)
    private Long groupId;

    @Column(name = "MEMTMID", updatable = false)
    private Long teamId;

    public UserMembershipJunction(@NonNull Long userId, @NonNull UserMembershipClaim claim) {
        this.userId = userId;
        this.companyNumber = claim.getCompanyNumber();
        this.regionId = claim.getRegionId();
        this.groupId = claim.getGroupId();
        this.teamId = claim.getTeamId();
    }
}
