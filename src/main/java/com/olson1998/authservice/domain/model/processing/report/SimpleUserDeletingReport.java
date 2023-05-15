package com.olson1998.authservice.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.processing.report.UserDeletingReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SimpleUserDeletingReport implements UserDeletingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "user_id")
    private final long userId;

    @JsonProperty("del_private_roles_qty")
    private final int deletedPrivateRolesQty;

    @JsonProperty("del_memberships_bindings_qty")
    private final int deletedMembershipsBindingsQty;

}
