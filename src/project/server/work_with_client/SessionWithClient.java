package project.server.work_with_client;

import project.common.messages.MessageType;
import project.common.messages.Message;
import project.common.messages.SocketStream;
import project.server.SessionManager;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.classes.Chats;
import project.server.work_with_client.classes.User;
import project.server.work_with_client.classes.Users;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.utils.Utils;

import java.util.Set;


// TODO:  по имени класса абсолютно непонятно чем он занимается
public class SessionWithClient {
    private SessionManager sessionManager;
    private User user;
    private SocketStream socketStream;
    private Set<ClientCommand> commands;

    public SessionWithClient(SessionManager sessionManager, SocketStream socketStream, Set<ClientCommand> commands) {
        this.sessionManager = sessionManager;
        this.socketStream = socketStream;
        this.commands = commands;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public Users users() {
        return sessionManager.getUsers();
    }

    public User getUser() {
        return user;
    }

    public User getUser(Long id) {
        return users().getUser(id);
    }

    public void setUser(String login) {
        if (users().getUser(login) == null) {
            user = users().createUser(login);
            chats().createChats(user.getId());
            sessionManager.loginUser(user.getId(), this);
        }
    }

    public void setUser(String login, String password) {
        user = users().getUser(login, password);
        if (user != null) {
            sessionManager.loginUser(user.getId(), this);
        }
    }

    public void clearUser() {
        sessionManager.logoutUser(user.getId());
        user = null;
    }

//    public void saveUser() {
//        users().setUser(user);
//    }

    public Chats chats() { return sessionManager.getChats(); }

    public Chat getUserChat(String id) {
        Long chatId;
        try {
            chatId = new Long(id);
        } catch (Exception e) {
            writeErrorToClient("wrong format of arguments");
            return null;
        }
        if (!Utils.checkChatExisting(this, chatId)) {
            return null;
        }
        if (!Utils.checkUserParticipating(this, chatId)) {
            return null;
        }
        return chats().getChat(chatId);
    }

    public String printCommandsHelp() {
        String answer = "";
        for (ClientCommand command : commands) {
            answer += "/" + command.name() + " " + command.help() + "\n";
            if (!command.example().equals("")) {
                answer += command.name() + " " + command.example() + "\n";
            }
            answer += "\n";
        }
        return answer;
    }

    public void writeToClient(Message message) {
        socketStream.write(message);
    }

    public void writeToClient(String type, String content) {
        writeToClient(new Message(MessageType.valueOf(type.toUpperCase()), content.trim()));
    }

    public void writeErrorToClient(String errorMessage) {
        socketStream.write(new Message(MessageType.ERROR, errorMessage.trim()));
    }
}
