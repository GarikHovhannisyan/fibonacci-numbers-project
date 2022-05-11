package org.example.secondclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NumberService {
    private final RSocketRequester rSocketRequester;

    @Autowired
    public NumberService(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @Cacheable("numbers")
    public Mono<Long> getSum(long from, long to) {
        Flux<Long> fibonacciNumbers = rSocketRequester
                .route("fibonacciNumbers")
                .retrieveFlux(Long.class);
        Flux<Long> requiredNumbers = fibonacciNumbers.filter(number -> number >= from && number <= to);
        return requiredNumbers.reduce(Long::sum).switchIfEmpty(Mono.just(0L));
    }
}
