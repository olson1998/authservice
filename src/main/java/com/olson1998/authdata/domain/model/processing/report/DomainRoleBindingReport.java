package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainRoleBindingReport implements RoleBindingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "saved_role_bindings")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Set<String>> savedRoleBindings;

    @JsonProperty(value = "saved_authorities")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, AuthorityDetails> persistedAuthoritiesDetailsMap;
}
