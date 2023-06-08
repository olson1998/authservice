package com.olson1998.authdata.domain.port.processing.report.stereotype;

import java.util.Map;

public interface RoleBoundsDeletingReport extends ProcessingReport {

    Map<String, Integer> getDeletedRolesBoundsQty();
}
