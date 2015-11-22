package project.server.work_with_client.classes;

import java.sql.Timestamp;

/**
 * Created by rikachka on 07.11.15.
 */
public class ChatMessage {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String text;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    ChatMessage(Long id, Long chatId, Long senderId, String text) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.text = text;
    }

    public ChatMessage(String[] messageInfo) throws Exception {
        if (messageInfo.length != 5) {
            throw new Exception("creating message: wrong number of fields");
        }
        try {
            id = new Long(messageInfo[0]);
            chatId = new Long(messageInfo[1]);
            senderId = new Long(messageInfo[2]);
            text = messageInfo[3];
            timestamp = Timestamp.valueOf(messageInfo[4]);
        } catch (Exception e) {
            throw new Exception("creating user: wrong format of arguments");
        }
    }

    public Long getId() { return id; }

    public Long getChatId() { return chatId; }

    public Long getSenderId() {
        return senderId;
    }

    public String getText() { return text; }

    public Timestamp getTimestamp() { return timestamp; }

    public String getMessage() {
        return "chat " + chatId + ": sender " + senderId + ": " + timestamp + ": " + text;
    }
}
