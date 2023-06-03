package com.olson1998.authservice.domain.port.processing.report.stereotype;

import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;

import java.util.Map;

public interface AuthoritySavingReport extends ProcessingReport {

    Map<String, AuthorityDetails> getPersistedAuthoritiesDetailsMap();
}
