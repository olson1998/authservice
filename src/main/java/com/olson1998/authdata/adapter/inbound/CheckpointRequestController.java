package com.olson1998.authdata.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/checkpoint")
public class CheckpointRequestController {

    @GetMapping(path = "/logs")
    public CompletableFuture<List<String>> getLogs(){
        return CompletableFuture.completedFuture(new ArrayList<>());
    }
}
