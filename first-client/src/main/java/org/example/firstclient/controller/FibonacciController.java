package org.example.firstclient.controller;

import org.example.firstclient.service.FibonacciService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class FibonacciController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FibonacciController.class);

    private final FibonacciService fibonacciService;

    public FibonacciController(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }

    @MessageMapping("fibonacciNumbers")
    public Flux<Long> getNumbers() {
        Flux<Long> numbers = fibonacciService.getFirstFiftyFibonacciNumbers();
        return numbers.delayElements(Duration.ofMillis(100)).doOnNext(number -> LOGGER.info("Server produces: {}", number));
    }
}
