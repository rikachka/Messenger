package project.common.messages;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by rikachka on 21.11.15.
 */
public class JsonProtocol {
    public static Object decode(String jsonString, Class objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object object = mapper.readValue(jsonString, objectClass);
            return object;
        } catch (IOException e) {
            throw new IOException("Can't convert json into message");
        }
    }

    public static String encode(Object object) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(object);
            return jsonString;
        } catch (IOException io) {
            throw new IOException("Can't convert message into json");
        }
    }
}
