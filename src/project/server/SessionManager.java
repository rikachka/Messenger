package project.server;

import project.common.messages.MessageType;
import project.common.messages.Message;
import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.classes.ChatMessage;
import project.server.work_with_client.classes.Chats;
import project.server.work_with_client.classes.Users;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.commands.chat_commands.*;
import project.server.work_with_client.commands.other_commands.HelpCommand;
import project.server.work_with_client.commands.user_commands.*;
import project.server.work_with_client.file_system.LoadDatabase;
import project.server.work_with_client.utils.Constants;
import project.server.work_with_client.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionManager {
    private Users users = new Users();
    private Chats chats = new Chats();
    private Map<Long, SessionWithClient> clients = new HashMap<>();
    private CommandHandlerClient commandHandler = new CommandHandlerClient();

    // TODO: нам не надо знать ни про файлы ни про БД. Все должно быть спрятано за интерфейс
    private File databaseDir;

    public SessionManager() throws Exception {
        databaseDir = Utils.makePathAbsolute(Constants.DATABASE_DIR).toFile();
        LoadDatabase.start(this);

        addCommand(new RegistrateCommand());
        addCommand(new LoginCommand());
        addCommand(new LogoutCommand());
        addCommand(new AddNicknameCommand());
        addCommand(new UserInfoCommand());
        addCommand(new ChangePasswordCommand());

        addCommand(new CreateCommand());
        addCommand(new FindCommand());
        addCommand(new HistoryCommand());
        addCommand(new ListCommand());
        addCommand(new SendCommand());

        addCommand(new HelpCommand());
    }

    private void addCommand(ClientCommand command) {
        commandHandler.addCommand(command);
    }

    public Users getUsers() {
        return users;
    }

    public Chats getChats() { return chats; }

    public File getDatabaseDir() { return databaseDir; }

    public CommandHandlerClient getCommandHandler() {
        return commandHandler;
    }

    public void loginUser(Long userId, SessionWithClient session) {
        clients.put(userId, session);
    }

    public void logoutUser(Long userId) {
        clients.remove(userId);
    }

    public void sendMessage(Chat chat, ChatMessage chatMessage) {
        Set<Long> receivers = new HashSet<>();
        receivers.addAll(chat.getParticipants());
        receivers.remove(chatMessage.getSenderId());
        clients.keySet().forEach(user -> {
            if (receivers.contains(user)) {
                SessionWithClient session = clients.get(user);
                session.writeToClient(new Message(MessageType.CHAT_SEND, chatMessage.getText()));
            }
        });
    }

    public void sendChatWelcome(Chat chat, Long userId) {
        Set<Long> receivers = new HashSet<>();
        receivers.addAll(chat.getParticipants());
        receivers.remove(userId);
        clients.keySet().forEach(user -> {
            if (receivers.contains(user)) {
                SessionWithClient session = clients.get(user);
                session.writeToClient(new Message(MessageType.CHAT_CREATE, "welcome to the chat " + chat.getId()));
            }
        });
    }
}
