package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authdata.application.mapping.exception.RoleDetailsMappingException;
import com.olson1998.authdata.domain.port.processing.tree.exception.DtoMappingException;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.*;

@EqualsAndHashCode
@RequiredArgsConstructor
public class RoleDetailsForm implements RoleDetails {

    @Getter
    @JsonProperty(value = NAME, required = true)
    private final String name;

    @JsonProperty(value = "subject", required = true)
    private final RoleSubject subject;

    @Getter
    @JsonProperty(value = USER_ID)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;

    @Getter
    @JsonProperty(value = COMPANY_NUMBER)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long companyNumber;

    @Getter
    @JsonProperty(value = REGION_ID)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String regionId;

    @Getter
    @JsonProperty(value = GROUP_ID)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long groupId;

    @Getter
    @JsonProperty(value = TEAM_ID)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long teamId;

    @JsonCreator
    public RoleDetailsForm(@JsonProperty(value = NAME, required = true) String name,
                           @JsonProperty(value = "subject", required = true) RoleSubject subject,
                           @JsonProperty(value = USER_ID) Long userId,
                           @JsonProperty(value = COMPANY_NUMBER) Long companyNumber,
                           @JsonProperty(value = REGION_ID) String regionId,
                           @JsonProperty(value = GROUP_ID) Long groupId,
                           @JsonProperty(value = TEAM_ID) Long teamId) {
        this.name = name;
        this.subject = subject;
        setValueOrThrow(
                subject,
                userId,
                companyNumber,
                regionId,
                groupId,
                teamId
        );
    }

    @Override
    public String getSubject() {
        return subject.name();
    }

    private void setValueOrThrow(RoleSubject subject,
                                 Long userId,
                                 Long companyNumber,
                                 String regionId,
                                 Long groupId,
                                 Long teamId) throws DtoMappingException {
        switch (subject){
            case PRIVATE -> {
                throwMappingExceptionIfValueNull(subject, userId);
                this.userId = userId;
            }
            case REGION -> {
                throwMappingExceptionIfValueNull(subject, regionId);
                this.regionId = regionId;
            }
            case GROUP -> {
                throwMappingExceptionIfValueNull(subject, groupId);
                this.groupId = groupId;
            }
            case TEAM -> {
                throwMappingExceptionIfValueNull(subject, teamId);
                this.teamId = teamId;
            }
            case COMPANY -> {
                throwMappingExceptionIfValueNull(subject, companyNumber);
                this.companyNumber = companyNumber;
            }
        }
    }

    private void throwMappingExceptionIfValueNull(RoleSubject subject, Object value) throws DtoMappingException {
        if(value == null){
            String jsonKey = null;
            switch (subject){
                case PRIVATE -> jsonKey = USER_ID;
                case COMPANY -> jsonKey = COMPANY_NUMBER;
                case REGION -> jsonKey = REGION_ID;
                case GROUP -> jsonKey= GROUP_ID;
                case TEAM -> jsonKey = TEAM_ID;
            }
            var reason = String.format("subject: '%s', requires non null value of json key: '%s'", subject, jsonKey);
            throw new RoleDetailsMappingException(RoleDetails.class, reason);
        }
    }
}
