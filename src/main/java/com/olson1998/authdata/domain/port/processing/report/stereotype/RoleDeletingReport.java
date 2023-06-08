package com.olson1998.authdata.domain.port.processing.report.stereotype;

import java.util.Map;

public interface RoleDeletingReport extends ProcessingReport {

    int getDeletedPrivateRolesQty();

    Map<String, Integer> getRoleDeletedBounds();
}
