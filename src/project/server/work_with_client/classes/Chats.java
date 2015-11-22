package project.server.work_with_client.classes;

import java.util.*;

/**
 * Created by rikachka on 07.11.15.
 */

// TODO: Нужен инетрфейс
public class Chats {
    private Map<Long, Chat> chats = new HashMap<>();
    private Map<Long, Set<Long>> userChats = new HashMap<>();

    public Set<Long> getChats(User user) {
        return userChats.get(user.getId());
    }

    public void createChats(Long userId) {
        userChats.put(userId, new HashSet<>());
    }

    public Chat getChat(Long chatId) {
        if (chats.containsKey(chatId)) {
            return chats.get(chatId);
        } else {
            return null;
        }
    }

    public Chat createChat(Set<Long> participants, User admin) {
        if (participants.size() == 2) {
            for (Long chatId : userChats.get(admin.getId())) {
                Chat chat = chats.get(chatId);
                boolean sameChat = true;
                if (chat.getParticipants().size() == participants.size()) {
                    for (Long user : participants) {
                        if (!chat.getParticipants().contains(user)) {
                            sameChat = false;
                        }
                    }
                    if (sameChat) {
                        return chat;
                    }
                }
            }
        }

        long chatId = chats.size() + 1;
        Chat chat = new Chat(chatId, admin.getId(), participants);
        chats.put(chatId, chat);
        addChatToUserChats(chat);
        return chat;
    }

    public Collection<Chat> getChats() {
        return chats.values();
    }

    public void addChat(Chat chat) {
        chats.put(chat.getId(), chat);
        addChatToUserChats(chat);
    }


    private void addChatToUserChats(Chat chat) {
        for (Long userId : chat.getParticipants()) {
            Set<Long> oldChats = userChats.get(userId);
            oldChats.add(chat.getId());
            userChats.put(userId, oldChats);
        }
    }
}
