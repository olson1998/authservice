package com.olson1998.authdata.domain.port.processing.request.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;

import java.util.Set;

public interface AuthoritySavingRequest extends Request {

    Set<AuthorityDetails> getAuthoritiesDetails();
}
