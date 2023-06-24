package com.olson1998.authdata.domain.model.processing.request.payload;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.Getter;

@Getter
public class PrivateRoleDetails implements RoleDetails {

    private final Long userId;

    private final String name;

    private final String subject = "PRIVATE";

    public PrivateRoleDetails(long userId) {
        this.userId = userId;
        this.name = new StringBuilder("USER&")
                .append(userId)
                .toString();
    }

    @Override
    public Long getCompanyNumber() {
        return null;
    }

    @Override
    public String getRegionId() {
        return null;
    }

    @Override
    public Long getGroupId() {
        return null;
    }

    @Override
    public Long getTeamId() {
        return null;
    }
}
