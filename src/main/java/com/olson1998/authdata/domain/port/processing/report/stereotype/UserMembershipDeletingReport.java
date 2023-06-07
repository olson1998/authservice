package com.olson1998.authdata.domain.port.processing.report.stereotype;

public interface UserMembershipDeletingReport extends ProcessingReport {

    Long getUserId();

    int getDeletedRegionMemberships();

    int getDeletedGroupMemberships();

    int getDeletedTeamMemberships();
}
