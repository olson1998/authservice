package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.application.requesting.entity.RoleDetailsForm;
import com.olson1998.authservice.domain.port.request.entity.RoleDetails;
import com.olson1998.authservice.domain.port.request.model.RoleSavingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.*;

@Getter
@RequiredArgsConstructor
public class ApplicationRoleSavingRequest implements RoleSavingRequest {

    private final UUID id;

    private final RoleDetails details;

    @JsonCreator
    public ApplicationRoleSavingRequest(@JsonProperty(value = ID, required = true) UUID id,
                                        @JsonProperty(value = NAME, required = true) String name,
                                        @JsonProperty(value = "subject", required = true) RoleSubject subject,
                                        @JsonProperty(value = USER_ID) Long userId,
                                        @JsonProperty(value = COMPANY_NUMBER) Long companyNumber,
                                        @JsonProperty(value = REGION_ID) String regionId,
                                        @JsonProperty(value = GROUP_ID) Long groupId,
                                        @JsonProperty(value = TEAM_ID) Long teamId) {
        this.id = id;
        this.details = new RoleDetailsForm(
                name,
                subject,
                userId,
                companyNumber,
                regionId,
                groupId,
                teamId
        );
    }
}
