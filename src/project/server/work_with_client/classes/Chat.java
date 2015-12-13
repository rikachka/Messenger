package project.server.work_with_client.classes;

import project.server.work_with_client.database.IdentifiedObject;

/**
 * Created by rikachka on 07.11.15.
 */
public class Chat implements IdentifiedObject {
    private Long chatId;
    private Long adminId;

    public Chat(Long chatId, Long adminId) {
        this.chatId = chatId;
        this.adminId = adminId;
    }

    public Chat(Long adminId) {
        this.adminId = adminId;
    }

    public Long getId() {
        return chatId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setId(Long id) {
        this.chatId = id;
    }
}
