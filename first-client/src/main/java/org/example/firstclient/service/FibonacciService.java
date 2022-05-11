package org.example.firstclient.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class FibonacciService {

    public Flux<Long> getFirstFiftyFibonacciNumbers() {
        List<Long> numbers = new ArrayList<>();
        long first = 0;
        long second = 1;
        numbers.add(first);
        numbers.add(second);
        int numbersCount = 2;
        while (numbersCount < 50) {
            long next = first + second;
            numbers.add(next);
            first = second;
            second = next;
            numbersCount++;
        }
        return Flux.fromIterable(numbers);
    }
}
