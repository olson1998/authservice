package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.developer.utils.JwtTokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Profile("developer")

@Async
@RestController
@RequestMapping(value = "/jwt")
public class JwtTokenController {

    private final JwtTokenFactory jwtTokenFactory;

    @GetMapping(value = "/create")
    public CompletableFuture<String> getDeveloperToken(@RequestParam("tid") String tid,
                                                       @RequestParam("uid") String uid){
        return jwtTokenFactory.createDeveloperJwt(tid, Long.parseLong(uid));
    }
}
