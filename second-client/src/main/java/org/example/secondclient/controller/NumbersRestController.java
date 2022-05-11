package org.example.secondclient.controller;


import org.example.secondclient.response.ResponseInfo;
import org.example.secondclient.service.NumberService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.secondclient.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/numbers")
public class NumbersRestController {
    private final NumberService numberService;

    public NumbersRestController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping(value = "/")
    public HttpEntity<?> getSum(@RequestParam(value = "from") int from,
                                @RequestParam(value = "to") int to) {
        if (from < 0 || to < 0) {
            return ResponseEntity.badRequest().body(ResponseInfo.createResponse(BAD_REQUEST, NEGATIVE_NUMBER));
        }
        if (from > to) {
            return ResponseEntity.badRequest().body(ResponseInfo.createResponse(BAD_REQUEST, FROM_IS_GREATER_THEN_TO));
        }
        return ResponseEntity.ok().body(numberService.getSum(from, to));

    }
}
