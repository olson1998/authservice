package com.olson1998.authdata.application.security.handler;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static com.olson1998.authdata.application.security.filter.CommonResponseHeaders.CHECKPOINT_VERIFICATION_FAILURE;
import static jakarta.servlet.http.HttpServletResponse.SC_SERVICE_UNAVAILABLE;

public class CheckpointAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(SC_SERVICE_UNAVAILABLE);
        if(exception instanceof CheckpointVerificationException error){
            response.setHeader(CHECKPOINT_VERIFICATION_FAILURE, error.getHeaderValue());
        }else {
            response.setHeader(CHECKPOINT_VERIFICATION_FAILURE, "unknown");
        }
    }
}
