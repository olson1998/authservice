package com.olson1998.authdata.application.requesting.model.payload;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserDetailsForm implements UserDetails {

    private final boolean enabled;

    private final String username;

    private final String password;

    private final Long userExpTime;

    private final ChronoUnit userExpTimeUnit;

    private final Long passExpTime;

    private final ChronoUnit passExpTimeUnit;

    @Override
    public Duration getUserExpDuration() {
        if(userExpTime != null){
            return Duration.of(
                    userExpTime,
                    userExpTimeUnit
            );
        }else {
            return null;
        }
    }

    @Override
    public Duration getPasswordExpDuration() {
        if(passExpTime != null){
            return Duration.of(
                    passExpTime,
                    passExpTimeUnit
            );
        }else {
            return null;
        }
    }

    @Override
    public boolean isUserPasswordExpiring() {
        return passExpTime != null;
    }

    @Override
    public boolean isUserExpiring() {
        return userExpTime != null;
    }
}
