package project.common.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by rikachka on 19.11.15.
 */
public class Message implements Serializable {
    private MessageType type = MessageType.DEFAULT;

    private String content = "";

    public Message() {}

    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
