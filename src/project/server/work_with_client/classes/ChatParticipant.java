package project.server.work_with_client.classes;

import project.server.work_with_client.database.IdentifiedObject;

/**
 * Created by rikachka on 07.11.15.
 */
public class ChatParticipant implements IdentifiedObject {
    private Long id;
    private Long chatId;
    private Long userId;

    public ChatParticipant(Long id, Long chatId, Long userId) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
    }

    public ChatParticipant(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public Long getId() { return id; }

    public Long getChatId() { return chatId; }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) { this.id = id; }
}
