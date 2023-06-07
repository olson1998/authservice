package com.olson1998.authservice.domain.port.processing.report.stereotype;

import java.util.Map;
import java.util.Set;

public interface RoleBindingReport extends ProcessingReport, AuthoritySavingReport {

    Map<String, Set<String>> getSavedRoleBindings();
}
