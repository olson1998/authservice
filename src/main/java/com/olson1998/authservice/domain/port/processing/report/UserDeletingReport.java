package com.olson1998.authservice.domain.port.processing.report;

public interface UserDeletingReport extends ProcessingReport{

    long getUserId();

    int getDeletedPrivateRolesQty();

    int getDeletedMembershipsBindingsQty();
}
