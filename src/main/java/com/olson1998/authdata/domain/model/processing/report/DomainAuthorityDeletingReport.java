package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DomainAuthorityDeletingReport implements AuthorityDeletingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "del_authorities_qty")
    private final int deletedAuthoritiesQty;
}
