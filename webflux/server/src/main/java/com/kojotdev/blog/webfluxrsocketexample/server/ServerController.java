package com.kojotdev.blog.webfluxrsocketexample.server;

import com.kojotdev.blog.webfluxrsocketexample.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ServerController {
    private static final Logger log = LoggerFactory.getLogger(ServerController.class);

    @MessageMapping("hello")
    public Mono<Message> helloServer(Message message) {
        log.info("got message in Server {}", message.message);
        return Mono.just(message)
                .map(msg -> new Message(msg.message + " | Server says hello!"));
    }
}
