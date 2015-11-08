package project.server.database;

import project.server.SessionManager;
import project.server.database.classes.Chat;
import project.server.database.classes.Chats;
import project.server.database.classes.User;
import project.server.database.classes.Users;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.io.PrintWriter;
import java.util.Set;

public class InterpreterStateDatabase {
    private SessionManager sessionManager;
    private User user;
    public PrintWriter out;
    private Set<DatabaseCommand> commands;

    public InterpreterStateDatabase(SessionManager sessionManager, PrintWriter out, Set<DatabaseCommand> commands) {
        this.sessionManager = sessionManager;
        this.out = out;
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
            sessionManager.loginUser(user.getId(), out);
        }
    }

    public void setUser(String login, String password) {
        user = users().getUser(login, password);
        if (user != null) {
            sessionManager.loginUser(user.getId(), out);
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
            this.out.println("wrong format of arguments");
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

    public void printCommandsHelp() {
        for (DatabaseCommand command : commands) {
            out.println(command.name() + " " + command.help());
            if (!command.example().equals("")) {
                out.println(command.name() + " " + command.example());
            }
            out.println();
        }
    }
}
