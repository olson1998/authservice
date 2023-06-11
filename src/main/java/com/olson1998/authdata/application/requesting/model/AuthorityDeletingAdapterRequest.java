package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class AuthorityDeletingAdapterRequest extends AbstractAdapterRequest implements AuthorityDeletingRequest {

    private final Set<String> authoritiesIds;

}
