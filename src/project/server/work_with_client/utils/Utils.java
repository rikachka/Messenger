package project.server.work_with_client.utils;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.database.exceptions.DataAccessException;

/**
 * Created by rikachka on 07.11.15.
 */


public class Utils {
    public static boolean checkUserAuthorised(SessionWithClient session) {
        if (session.getUser() == null) {
            session.writeErrorToClient("you are not authorised");
            return false;
        }
        return true;
    }

    public static boolean checkUserNotAuthorised(SessionWithClient session) {
        if (session.getUser() != null) {
            session.writeErrorToClient("you are already authorised");
            return false;
        }
        return true;
    }

    public static boolean checkChatExisting(SessionWithClient session, Long chatId) throws DataAccessException {
        if (session.chats().getChat(chatId) == null) {
            session.writeErrorToClient("no chat " + chatId);
            return false;
        }
        return true;
    }

    public static boolean checkUserExisting(SessionWithClient session, Long userId) throws DataAccessException {
        if (session.users().getUser(userId) == null) {
            session.writeErrorToClient("no user " + userId);
            return false;
        }
        return true;
    }

    public static boolean checkUserParticipating(SessionWithClient session, Long chatId) throws DataAccessException {
        if (!session.chats().getChatParticipants(chatId).contains(session.getUser().getId())) {
            session.writeErrorToClient("you do not participate chat " + chatId);
            return false;
        }
        return true;
    }
}
