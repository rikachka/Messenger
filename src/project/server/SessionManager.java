package project.server;

import project.server.database.classes.Chat;
import project.server.database.classes.Chats;
import project.server.database.classes.Message;
import project.server.database.classes.Users;
import project.server.database.file_system.LoadDatabase;
import project.server.database.utils.Constants;
import project.server.database.utils.Utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionManager {
    private Users users = new Users();
    private Chats chats = new Chats();
    private Map<Long, PrintWriter> clients = new HashMap<>();

    // TODO: нам не надо знать ни про файлы ни про БД. Все должно быть спрятано за интерфейс
    private File databaseDir;

    public SessionManager() throws Exception {
        databaseDir = Utils.makePathAbsolute(Constants.DATABASE_DIR).toFile();
        LoadDatabase.start(this);
    }

    public Users getUsers() {
        return users;
    }

    public Chats getChats() { return chats; }

    public File getDatabaseDir() { return databaseDir; }

    public void loginUser(Long userId, PrintWriter out) {
        clients.put(userId, out);
    }

    public void logoutUser(Long userId) {
        clients.remove(userId);
    }

    public void sendMessage(Chat chat, Message message) {
        Set<Long> receivers = new HashSet<>();
        receivers.addAll(chat.getParticipants());
        receivers.remove(message.getSenderId());
        for (Long user : clients.keySet()) {
            if (receivers.contains(user)) {
                PrintWriter out = clients.get(user);
                out.println(message.getText());
            }
        }
    }

    public void sendChatWelcome(Chat chat, Long userId) {
        Set<Long> receivers = new HashSet<>();
        receivers.addAll(chat.getParticipants());
        receivers.remove(userId);
        for (Long user : clients.keySet()) {
            if (receivers.contains(user)) {
                PrintWriter out = clients.get(user);
                out.println("welcome to the chat " + chat.getId());
            }
        }
    }
}
