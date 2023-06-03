package com.olson1998.authservice.domain.port.processing.report.stereotype;

import java.util.Map;

public interface RoleBindingReport extends ProcessingReport, AuthoritySavingReport {

    Map<String, String> getSavedRoleBindings();
}
