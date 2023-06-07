package com.olson1998.authdata.domain.port.processing.report.stereotype;

public interface UserDeletingReport extends ProcessingReport{

    long getUserId();

    int getDeletedPrivateRolesQty();

    int getDeletedMembershipsBindingsQty();
}
