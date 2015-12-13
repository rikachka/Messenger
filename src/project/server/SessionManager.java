package project.server;

import project.common.messages.MessageType;
import project.common.messages.Message;
import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.*;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.commands.chat_commands.*;
import project.server.work_with_client.commands.other_commands.HelpCommand;
import project.server.work_with_client.commands.user_commands.*;
import project.server.work_with_client.database.*;
import project.server.work_with_client.database.exceptions.DataAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionManager {
    private TableProvider tableProvider;
    private UserStore users;
    private MessageStore chats;
    private Map<Long, SessionWithClient> clients = new HashMap<>();
    private CommandHandlerClient commandHandler = new CommandHandlerClient();

    public SessionManager() throws Exception {
        tableProvider = new TableProvider();
        users = new Users(new DaoUser(tableProvider));
        chats = new Chats(new DaoChat(tableProvider), new DaoChatMessage(tableProvider), new DaoChatParticipant(tableProvider));

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

    public UserStore getUsers() {
        return users;
    }

    public MessageStore getChats() { return chats; }

    public CommandHandlerClient getCommandHandler() {
        return commandHandler;
    }

    public void loginUser(Long userId, SessionWithClient session) {
        clients.put(userId, session);
    }

    public void logoutUser(Long userId) {
        clients.remove(userId);
    }

    public void sendMessage(Chat chat, ChatMessage chatMessage) throws DataAccessException {
        Set<Long> receivers = chats.getChatParticipants(chat.getId());
        receivers.remove(chatMessage.getSenderId());
        clients.keySet().forEach(user -> {
            if (receivers.contains(user)) {
                SessionWithClient session = clients.get(user);
                session.writeToClient(new Message(MessageType.CHAT_SEND, chatMessage.getText()));
            }
        });
    }

    public void sendChatWelcome(Chat chat, Long userId) throws DataAccessException {
        Set<Long> receivers = chats.getChatParticipants(chat.getId());
        receivers.remove(userId);
        clients.keySet().forEach(user -> {
            if (receivers.contains(user)) {
                SessionWithClient session = clients.get(user);
                session.writeToClient(new Message(MessageType.CHAT_CREATE, "welcome to the chat " + chat.getId()));
            }
        });
    }
}
