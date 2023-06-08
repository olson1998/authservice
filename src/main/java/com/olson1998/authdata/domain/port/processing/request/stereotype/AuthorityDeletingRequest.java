package com.olson1998.authdata.domain.port.processing.request.stereotype;

import java.util.Set;

public interface AuthorityDeletingRequest extends Request {

    Set<String> getAuthoritiesIds();
}
