package project.server;

import project.common.messages.MessageType;
import project.common.messages.Message;
import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommandHandlerClient {
    private final String PARAM_DELIMITER = "\\s+";
    
    private HashMap<MessageType, ClientCommand> allCommands = new HashMap<>();
    
    public void addCommand(ClientCommand command) {
        allCommands.put(MessageType.valueOf(command.name().toUpperCase()), command);
    }

    public void startCommand(SessionWithClient session, Message message) throws Exception {
        MessageType messageType = message.getType();
        String commandContent = message.getContent().trim();
        String[] args = new String[0];
        if (!commandContent.equals("")) {
            if (messageType == MessageType.CHAT_SEND) {
                args = commandContent.split(PARAM_DELIMITER, 2);
            } else {
                args = commandContent.split(PARAM_DELIMITER);
            }
        }
        if (!allCommands.containsKey(messageType)) {
            System.out.println(messageType.toString());
            throw new Exception("No such command");
        }
        ClientCommand command = allCommands.get(messageType);

        if ((command.minArgs() > args.length) || (command.maxArgs() < args.length)) {
            throw new Exception(command.name() + ": Wrong number of arguments - " + args.length);
        }
        try {
            command.execute(session, args);
//        } catch (IOException e) {
//            throw new Exception(command.name() + ": Error");
//        } catch (DataBaseException e) {
//            throw new DataBaseException("DataBase: " + e.getMessage());
//        } catch (ThreadInterruptException e) {
//            throw new ThreadInterruptException();
//        } catch (IndexOutOfBoundsException e) {
//            throw new Exception("wrong type (" + e.getMessage() + ")");
//        } catch (ColumnFormatException e) {
//            throw new Exception("wrong type (" + e.getMessage() + ")");
        } catch (Exception e) {
            throw new Exception(command.name() + ": " + e.getMessage());
        }
    }

    public Set<ClientCommand> getCommands() {
        Set<ClientCommand> commands = new HashSet<>();
        commands.addAll(allCommands.values());
        return commands;
    }
}


