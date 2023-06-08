package com.olson1998.authdata.domain.port.processing.report.stereotype;

public interface RoleDeletingReport extends ProcessingReport {

    int getDeletedPrivateRolesQty();

    int getDeletedBoundsQty();
}
