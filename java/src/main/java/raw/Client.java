package raw;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Client {

    private static Logger log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    static {
        log.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        final Client client = new Client();
        final RSocket rSocket = client.connect();
        client.fireAndForget(rSocket);
        client.requestResponse(rSocket);
        client.requestStream(rSocket);
        client.requestChannel(rSocket);
    }

    private RSocket connect() {
        return RSocketFactory.connect()
                .transport(WebsocketClientTransport.create(8801))
                .start()
                .block();
    }

    private void fireAndForget(RSocket rSocket) {
        log.info("sending fire and forget from client");
        Flux.just(new Message("fire and forget JAVA client!"))
                .map(msg -> MessageMapper.messageToJson(msg))
                .map(json -> DefaultPayload.create(json))
                .flatMap(message -> rSocket.fireAndForget(message))
                .blockLast();
    }

    private void requestResponse(RSocket rSocket) {
        log.info("sending request-response from client");
        Flux.just(new Message("requestResponse from JAVA client!"))
                .map(msg -> MessageMapper.messageToJson(msg))
                .map(json -> DefaultPayload.create(json))
                .flatMap(message -> rSocket.requestResponse(message))
                .map(payload -> payload.getDataUtf8())
                .doOnNext(payloadString -> {
                    log.info("got response in JAVA client");
                    log.info(payloadString);
                })
                .blockLast();
    }

    private void requestStream(RSocket rSocket) {
        log.info("sending request-stream from client");
        Flux.just(new Message("requestStream from JAVA client!"))
                .map(msg -> MessageMapper.messageToJson(msg))
                .map(json -> DefaultPayload.create(json))
                .flatMap(message -> rSocket.requestStream(message))
                .map(payload -> payload.getDataUtf8())
                .doOnNext(payloadString -> log.info(payloadString))
                .blockLast();
    }

    private void requestChannel(RSocket rSocket) {
        log.info("sending request-channel from client");
        final Flux<Payload> requestPayload = Flux.range(0, 5)
                .map(count -> new Message("requestChannel from JAVA client! #" + count))
                .map(msg -> {
                    log.info("sending message: {}", msg.message);
                    return MessageMapper.messageToJson(msg);
                })
                .map(json -> DefaultPayload.create(json));

        rSocket
                .requestChannel(requestPayload)
                .map(payload -> payload.getDataUtf8())
                .doOnNext(payloadString -> log.info(payloadString))
                .blockLast();
    }
}
