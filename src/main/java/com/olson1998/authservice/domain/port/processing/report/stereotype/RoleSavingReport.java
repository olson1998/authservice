package com.olson1998.authservice.domain.port.processing.report.stereotype;

import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;

import java.util.Map;

public interface RoleSavingReport extends ProcessingReport {

    Map<String, RoleDetails> getPersistedRolesDetailsMap();
}
