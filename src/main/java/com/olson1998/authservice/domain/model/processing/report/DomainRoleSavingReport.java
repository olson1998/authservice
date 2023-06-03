package com.olson1998.authservice.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainRoleSavingReport implements RoleSavingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "roles_map")
    private final Map<String, RoleDetails> persistedRolesDetailsMap;
}
