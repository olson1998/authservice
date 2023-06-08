package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainRoleDeletingReport implements RoleDeletingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "del_private_roles")
    private final int deletedPrivateRolesQty;

    @JsonProperty(value = "del_bounds")
    private final Map<String, Integer> roleDeletedBounds;
}
