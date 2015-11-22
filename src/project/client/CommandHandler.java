package project.client;

import project.client.commands.Command;
import project.client.exceptions.NotCriticalException;

import java.util.HashMap;

public class CommandHandler {
    private final String PARAM_DELIMITER = "\\s+";
    private final String SEND_COMMAND_PREFIX ="/";
    
    private HashMap<String, Command> allCommands = new HashMap<>();
    private Command defaultCommand = null;
    
    public void addCommand(Command command) {
        allCommands.put(command.name(), command);
    }

    void setDefaultCommand(Command command) {
        defaultCommand = command;
    }

    public void startCommand(Session session, String commandWithArgs) throws Exception {
        String[] args;
        if (commandWithArgs.startsWith(SEND_COMMAND_PREFIX)) {
            args = commandWithArgs.trim().split(PARAM_DELIMITER, 2);
        } else {
            args = commandWithArgs.trim().split(PARAM_DELIMITER);
        }
        if (args[0].isEmpty()) {
            return;
        }
        Command command = allCommands.getOrDefault(args[0], defaultCommand);
        
        if ((command.minArgs() > args.length) || (command.maxArgs() < args.length)) {
            throw new NotCriticalException(command.name() + ": Wrong number of arguments");
        }
        try {
            command.execute(session, args);
        } catch (Exception e) {
            throw new Exception(command.name() + ": " + e.getMessage());
        }
    }
}


