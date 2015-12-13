package project.server.work_with_client.classes;

import project.server.work_with_client.database.IdentifiedObject;

import java.sql.Timestamp;

/**
 * Created by rikachka on 07.11.15.
 */
public class ChatMessage implements IdentifiedObject {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String text;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public ChatMessage(Long id, Long chatId, Long senderId, String text, Timestamp timestamp) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public ChatMessage(Long chatId, Long senderId, String text) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.text = text;
    }

    public Long getId() { return id; }

    public Long getChatId() { return chatId; }

    public Long getSenderId() {
        return senderId;
    }

    public String getText() { return text; }

    public Timestamp getTimestamp() { return timestamp; }

    public void setId(Long id) { this.id = id; }

    public String getMessage() {
        return "chat " + chatId + ": sender " + senderId + ": " + timestamp + ": " + text;
    }
}
