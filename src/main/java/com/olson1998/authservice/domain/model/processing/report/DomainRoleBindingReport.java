package com.olson1998.authservice.domain.model.processing.report;

import com.olson1998.authservice.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainRoleBindingReport implements RoleBindingReport {

    private final UUID requestId;

    private final Map<String, String> savedRoleBindings;

    private final Map<String, AuthorityDetails> persistedAuthoritiesDetailsMap;
}
