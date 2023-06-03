package com.olson1998.authservice.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.data.stereotype.User;
import com.olson1998.authservice.domain.port.processing.report.stereotype.UserSavingReport;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainUserSavingReport implements UserSavingReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "user_id")
    private final long userId;

    @JsonProperty(value = "username")
    private final String username;

    public DomainUserSavingReport(@NonNull UUID requestId, @NonNull User user) {
        this.requestId = requestId;
        this.userId = user.getId();
        this.username = user.getUsername();
    }
}
