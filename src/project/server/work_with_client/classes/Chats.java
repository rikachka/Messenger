package project.server.work_with_client.classes;

import project.server.work_with_client.database.DaoChat;
import project.server.work_with_client.database.DaoChatMessage;
import project.server.work_with_client.database.DaoChatParticipant;
import project.server.work_with_client.database.exceptions.DataAccessException;

import java.util.*;

/**
 * Created by rikachka on 07.11.15.
 */

public class Chats implements MessageStore {
    private DaoChat daoChat;
    private DaoChatMessage daoChatMessage;
    private DaoChatParticipant daoChatParticipant;

    public Chats(DaoChat daoChat, DaoChatMessage daoChatMessage, DaoChatParticipant daoChatParticipant) {
        this.daoChat = daoChat;
        this.daoChatMessage = daoChatMessage;
        this.daoChatParticipant = daoChatParticipant;
    }

    public Set<Long> getChats(User user) throws DataAccessException {
        Set<Long> chats = new HashSet<>();
        List<ChatParticipant> chatParticipants = daoChatParticipant.getByUserId(user.getId());
        for (ChatParticipant chatParticipant: chatParticipants) {
            chats.add(chatParticipant.getChatId());
        }
        return chats;
    }

    public Set<Long> getChatParticipants(Long chatId) throws DataAccessException {
        Set<Long> users = new HashSet<>();
        List<ChatParticipant> chatParticipants = daoChatParticipant.getByChatId(chatId);
        for (ChatParticipant chatParticipant: chatParticipants) {
            users.add(chatParticipant.getUserId());
        }
        return users;
    }

//    public void createChats(Long userId) {
//        userChats.put(userId, new HashSet<>());
//    }

    public Chat getChat(Long chatId) throws DataAccessException {
        return daoChat.getById(chatId);
    }

    public Chat createChat(Set<Long> participants, User admin) throws DataAccessException {
        if (participants.size() == 2) {
            for (Long chatId : getChats(admin)) {
                Set<Long> chatParticipants = getChatParticipants(chatId);
                Chat chat = daoChat.getById(chatId);
                boolean sameChat = true;
                if (chatParticipants.size() == participants.size()) {
                    for (Long user : participants) {
                        if (!chatParticipants.contains(user)) {
                            sameChat = false;
                        }
                    }
                    if (sameChat) {
                        return chat;
                    }
                }
            }
        }

        Chat chat = new Chat(admin.getId());
        daoChat.insert(chat);
        for (Long userId: participants) {
            ChatParticipant chatParticipant = new ChatParticipant(chat.getId(), userId);
            daoChatParticipant.insert(chatParticipant);
        }
        return chat;
    }



    public ChatMessage addMessage(Chat chat, Long senderId, String text) throws DataAccessException {
        ChatMessage chatMessage = new ChatMessage(chat.getId(), senderId, text);
        daoChatMessage.insert(chatMessage);
        return chatMessage;
    }

    public List<String> getMessagesTexts(Chat chat) throws DataAccessException {
        List<ChatMessage> messages = daoChatMessage.getByChatId(chat.getId());
        List<String> texts = new ArrayList<>();
        for (ChatMessage chatMessage : messages) {
            texts.add(chatMessage.getMessage());
        }
        return texts;
    }

    public List<String> getMessagesTexts(Chat chat, String searching) throws DataAccessException {
        List<String> allTexts = getMessagesTexts(chat);
        List<String> foundTexts = new ArrayList<>();
        for (String text : allTexts) {
            if (text.contains(searching)) {
                foundTexts.add(text);
            }
        }
        return foundTexts;
    }
}
