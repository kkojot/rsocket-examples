package com.kojotdev.blog.webfluxrsocketexample.rest;

import com.kojotdev.blog.webfluxrsocketexample.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    public final RSocketRequester rSocketRequester;

    public MessageController(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    @PostMapping("/message")
    public Mono<String> message(@RequestBody Message message) {
        log.info("got message in the rest request {}", message.message);
        return rSocketRequester
                .route("hello")
                .data(message)
                .retrieveMono(Message.class)
                .map(serverMessage -> serverMessage.message)
                .doOnNext(serverMsg -> log.info("server response in the client: {}", serverMsg));
    }
}
