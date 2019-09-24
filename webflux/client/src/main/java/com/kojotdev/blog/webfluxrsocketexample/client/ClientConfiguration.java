package com.kojotdev.blog.webfluxrsocketexample.client;

import io.rsocket.transport.netty.client.WebsocketClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

import java.net.URI;

@Configuration
public class ClientConfiguration {

    @Bean
    public RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
        final URI uri = URI.create("ws://localhost:8080/rsocket");
        return RSocketRequester
                .builder()
                .rsocketStrategies(rSocketStrategies)
                .connect(WebsocketClientTransport.create(uri))
                .block();
    }
}
