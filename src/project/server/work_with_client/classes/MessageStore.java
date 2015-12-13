package project.server.work_with_client.classes;

import project.server.work_with_client.database.exceptions.DataAccessException;

import java.util.List;
import java.util.Set;

/**
 * Created by rikachka on 02.12.15.
 */
public interface MessageStore {

    Set<Long> getChats(User user) throws DataAccessException;

    Set<Long> getChatParticipants(Long chatId) throws DataAccessException;

    Chat getChat(Long chatId) throws DataAccessException;

    Chat createChat(Set<Long> participants, User admin) throws DataAccessException;



    ChatMessage addMessage(Chat chat, Long senderId, String text) throws DataAccessException;

    public List<String> getMessagesTexts(Chat chat) throws DataAccessException;

    public List<String> getMessagesTexts(Chat chat, String searching) throws DataAccessException;
}
