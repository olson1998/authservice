package com.olson1998.authservice.domain.model.exception.request;

import com.olson1998.authservice.domain.port.data.stereotype.User;
import com.olson1998.authservice.domain.port.request.exception.DataPolicyViolationException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserPolicyViolationException extends DataPolicyViolationException {

    private static final String[] POLICY = {
            "username can not be null",
            "password can not be null",
            "username length should be greater than 8",
            "password should be greater than 6"
    };

    private final UUID requestId;

    private final int violatedParagraph;

    private final String violatedPolicy;

    private final String message;

    public UserPolicyViolationException(UUID requestId, int violatedParagraph) {
        this.requestId = requestId;
        this.violatedParagraph = violatedParagraph;
        this.violatedPolicy = POLICY[violatedParagraph];
        this.message = String.format(
                "violated policy of %s, violated paragraph: '%s': %s",
                User.class.getName(),
                violatedParagraph,
                violatedPolicy
        );
    }
}
