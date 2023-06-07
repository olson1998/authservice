package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainAuthoritiesSavingReport implements AuthoritySavingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    /**
     * Map where key is authority id and value is authority name
     */
    @JsonProperty(value = "authorities_map")
    private final Map<String, AuthorityDetails> persistedAuthoritiesDetailsMap;
}
