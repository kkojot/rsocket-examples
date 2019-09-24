package raw;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import io.rsocket.util.DefaultPayload;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class Server {

    private static Logger log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    static {
        log.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        RSocketFactory.receive()
                .acceptor((setup, sendingSocket) -> Mono.just(new DefaultSimpleService()))
                .transport(WebsocketServerTransport.create(8801))
                .start()
                .block()
                .onClose()
                .block();
    }

    private static final class DefaultSimpleService extends AbstractRSocket {

        @Override
        public Mono<Void> fireAndForget(Payload payload) {
            log.info("got fireAndForget in Server");
            log.info(payload.getDataUtf8());
            return Mono.empty();
        }

        @Override
        public Mono<Payload> requestResponse(Payload payload) {
            log.info("got requestResponse in Server");
            log.info(payload.getDataUtf8());
            return Mono.just(payload.getDataUtf8())
                    .map(payloadString -> MessageMapper.jsonToMessage(payloadString))
                    .map(message -> message.message + " | requestReponse from Server #1")
                    .map(responseText -> new Message(responseText))
                    .map(responseMessage -> MessageMapper.messageToJson(responseMessage))
                    .map(responseJson -> DefaultPayload.create(responseJson));
        }

        @Override
        public Flux<Payload> requestStream(Payload payload) {
            log.info("got requestStream in Server");
            log.info(payload.getDataUtf8());
            return Mono.just(payload.getDataUtf8())
                    .map(payloadString -> MessageMapper.jsonToMessage(payloadString))
                    .flatMapMany(msg -> Flux.range(0, 5)
                            .map(count -> msg.message + " | requestStream from Server #" + count)
                            .map(responseText -> new Message(responseText))
                            .map(responseMessage -> MessageMapper.messageToJson(responseMessage)))
                    .map(message -> DefaultPayload.create(message));
        }
    }

}
