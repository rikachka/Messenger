package project.common.messages;

import java.io.*;

/**
 * Created by rikachka on 21.11.15.
 */
public class SerializationProtocol implements Protocol {
    public Message decode(byte[] bytes) {
        if (bytes != null) {
            try {
                return (Message) JsonProtocol.decode(new String(bytes), Message.class);
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't decode message: " + e.getMessage());
            }
        }
        return null;
    }

    public byte[] encode(Message message) {
        if (message != null) {
            try {
                return JsonProtocol.encode(message).getBytes();
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't encode message: " + e.getMessage());
            }
        }
        return null;
    }
}
