package com.olson1998.authdata.domain.model.security;

import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CheckpointAuthentication implements Authentication {

    private boolean authenticated;

    private final boolean isError;

    private final String xCheckpointToken;

    private final CheckpointTimestamp checkpointTimestamp;

    private final Checkpoint checkpoint;
    private final CheckpointVerificationException checkpointVerificationException;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return checkpoint;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean isError() {
        return isError;
    }

    public String getxCheckpointToken() {
        return xCheckpointToken;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public CheckpointTimestamp getCheckpointTimestamp() {
        return checkpointTimestamp;
    }

    public CheckpointVerificationException getCheckpointVerificationException() {
        return checkpointVerificationException;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return CheckpointAuthentication.class.getName();
    }

    public CheckpointAuthentication(CheckpointVerificationException checkpointVerificationException) {
        this.isError = true;
        this.authenticated = false;
        this.checkpoint = null;
        this.xCheckpointToken = null;
        this.checkpointTimestamp = null;
        this.checkpointVerificationException = checkpointVerificationException;
    }

    public CheckpointAuthentication(String xCheckpointToken, Checkpoint checkpoint, CheckpointTimestamp checkpointTimestamp) {
        this.isError = false;
        this.authenticated = true;
        this.checkpoint = checkpoint;
        this.checkpointTimestamp = checkpointTimestamp;
        this.xCheckpointToken = xCheckpointToken;
        this.checkpointVerificationException = null;
    }
}
