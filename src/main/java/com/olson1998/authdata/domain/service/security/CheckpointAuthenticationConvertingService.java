package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.model.exception.security.CheckpointRequiredException;
import com.olson1998.authdata.domain.model.exception.security.MissingXCheckpointTokenHeader;
import com.olson1998.authdata.domain.model.security.CheckpointAuthentication;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.CheckpointProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckpointAuthenticationConvertingService implements CheckpointAuthenticationConverter {

    private static final String CHECKPOINT_TOKEN_HEADER = "X-Checkpoint-token";

    private final CheckpointProvider checkpointProvider;

    @Override
    public Authentication convert(HttpServletRequest request) {
        try{
            var token = getCheckpointToken(request);
            var timestamp = getCheckpointTimestamp(token);
            var checkpoint = getCheckpoint(token, timestamp);
            return new CheckpointAuthentication(token, checkpoint, timestamp);
        }catch (CheckpointVerificationException e){
            return new CheckpointAuthentication(e);
        }
    }

    private String getCheckpointToken(HttpServletRequest request){
        try{
            return Optional.ofNullable(request.getHeader(CHECKPOINT_TOKEN_HEADER))
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new MissingXCheckpointTokenHeader(e);
        }
    }

    private CheckpointTimestamp getCheckpointTimestamp(String token){
        try{
            return checkpointProvider.getCheckpointTimestamp(token)
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new CheckpointRequiredException(e);
        }
    }

    private Checkpoint getCheckpoint(String token, CheckpointTimestamp timestamp){
        try{
            return checkpointProvider.getCheckpoint(token, timestamp)
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new CheckpointRequiredException(e);
        }
    }
}
