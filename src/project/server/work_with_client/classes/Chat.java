package project.server.work_with_client.classes;

import java.util.*;

/**
 * Created by rikachka on 07.11.15.
 */
public class Chat {
    private Long chatId;
    private Long adminId;
    private Set<Long> participants = new HashSet<>();
    private Map<Long, ChatMessage> messages = new HashMap<>();

    Chat(Long chatId, Long adminId, Set<Long> participants) {
        this.chatId = chatId;
        this.adminId = adminId;
        this.participants.addAll(participants);
    }

    public Chat(String chatId, String adminId, String[] participants) throws Exception {
        if (participants.length < 2) {
            throw new Exception("creating chat: too few participants");
        }
        try {
            this.chatId = new Long(chatId);
            this.adminId = new Long(adminId);
            for (String user : participants) {
                this.participants.add(new Long(user));
            }
        } catch (Exception e) {
            throw new Exception("creating chat: wrong format of arguments");
        }
    }

    public Long getId() {
        return chatId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public Set<Long> getParticipants() {
        return participants;
    }

    public List<String> getMessagesTexts() {
        List<String> texts = new ArrayList<>();
        for (ChatMessage chatMessage : messages.values()) {
            texts.add(chatMessage.getMessage());
        }
        return texts;
    }

    public List<String> getMessagesTexts(String searching) {
        List<String> allTexts = getMessagesTexts();
        List<String> foundTexts = new ArrayList<>();
        for (String text : allTexts) {
            if (text.contains(searching)) {
                foundTexts.add(text);
            }
        }
        return foundTexts;
    }

    public ChatMessage addMessage(Long senderId, String text) {
        long messageId = messages.size() + 1;
        ChatMessage chatMessage = new ChatMessage(messageId, chatId, senderId, text);
        messages.put(messageId, chatMessage);
        return chatMessage;
    }

    public Collection<ChatMessage> getMessages() {
        return messages.values();
    }

    public void addMessage(ChatMessage chatMessage) {
        messages.put(chatMessage.getId(), chatMessage);
    }
}
