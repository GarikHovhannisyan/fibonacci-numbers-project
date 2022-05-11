package org.example.secondclient;

import org.example.secondclient.controller.NumbersRestController;
import org.example.secondclient.response.ResponseInfo;
import org.example.secondclient.service.NumberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.example.secondclient.response.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@WebFluxTest(NumbersRestController.class)
public class NumbersRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @MockBean
    NumberService numberService;

    @MockBean
    RSocketRequester rSocketRequester;


    @Test
    public void getSumTest() {
        Mono<Long> sum = Mono.just(20L);
        Mockito.when(numberService.getSum(0, 10))
                .thenReturn(sum);
        this.webClient.get().uri("/numbers/?from=0&to=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class).isEqualTo(20L);
    }

    @Test
    public void getSumTest2() {
        Mono<Long> sum = Mono.just(0L);
        Mockito.when(numberService.getSum(40, 50))
                .thenReturn(sum);
        this.webClient.get().uri("/numbers/?from=40&to=50")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class).isEqualTo(0L);
    }

    @Test
    public void getSumTest3() {
        this.webClient.get().uri("/numbers/?from=-1&to=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ResponseInfo.class).isEqualTo(ResponseInfo.createResponse(BAD_REQUEST, NEGATIVE_NUMBER));
    }

    @Test
    public void getSumTest4() {
        this.webClient.get().uri("/numbers/?from=20&to=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ResponseInfo.class).isEqualTo(ResponseInfo.createResponse(BAD_REQUEST, FROM_IS_GREATER_THEN_TO));
    }
}