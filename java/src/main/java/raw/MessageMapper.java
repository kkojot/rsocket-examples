package raw;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageMapper {
    private static ObjectMapper jsonMapper = new ObjectMapper();

    public static String messageToJson(Message msg) {
        try {
            return jsonMapper.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Message jsonToMessage(String json) {
        try {
            return jsonMapper.readValue(json, Message.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
