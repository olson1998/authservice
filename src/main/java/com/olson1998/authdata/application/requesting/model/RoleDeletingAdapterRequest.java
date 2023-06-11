package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class RoleDeletingAdapterRequest extends AbstractAdapterRequest implements RoleDeletingRequest {

    private final Set<String> roleIdSet;

}
