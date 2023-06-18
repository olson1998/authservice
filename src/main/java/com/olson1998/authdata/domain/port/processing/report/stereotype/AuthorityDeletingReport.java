package com.olson1998.authdata.domain.port.processing.report.stereotype;

import java.util.Map;
import java.util.Set;

public interface AuthorityDeletingReport extends ProcessingReport{

    int getDeletedAuthoritiesQty();

    int getDeletedRoleBounds();

    Set<String> getRoleIdWithUpdatedTimestamps();
}
