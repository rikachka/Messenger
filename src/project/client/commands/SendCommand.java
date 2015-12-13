package project.client.commands;

import project.client.Session;
import project.client.exceptions.CriticalException;
import project.client.exceptions.NotCriticalException;
import project.common.messages.MessageType;
import project.common.messages.Message;

import java.io.IOException;

public class SendCommand implements Command {
    private final String SEND_COMMAND_PREFIX ="/";

    public void execute(Session session, String[] args) throws NotCriticalException, CriticalException {
        if (session.getSocket().isInputShutdown()) {
            throw new CriticalException("Server disconnected");
        }
        Message message;
        try {
            String commandName = args[0].substring(SEND_COMMAND_PREFIX.length());
            MessageType messageType = MessageType.valueOf(commandName.toUpperCase());
            String commandArgs = "";
            if (args.length > 1) {
                commandArgs = args[1];
            }
            message = new Message(messageType, commandArgs);
        } catch (IllegalArgumentException e) {
            throw new NotCriticalException("No such command");
        }
        session.socketStream.write(message);
    }
    
    public String name() {
        return "send";
    }
    
    public int minArgs() { //name of the command is not given as arg[0]
        return 1;
    }
    
    public int maxArgs() {
        return 2;
    }
}
