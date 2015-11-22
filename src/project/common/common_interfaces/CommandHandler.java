package project.common.common_interfaces;

import java.util.HashMap;

public class CommandHandler {
    
    private HashMap<String, Command> allCommands = new HashMap<>();
    private Command defaultCommand = null;
    private String[] args;
    
    public void addCommand(Command command) {
        allCommands.put(command.name(), command);
    }

    void setDefaultCommand(Command command) {
        defaultCommand = command;
    }

    public void startCommand(String commandWithArgs) throws Exception {
        args = commandWithArgs.trim().split("\\s+");
        if (args[0].isEmpty()) {
            return;
        }
        Command command = allCommands.getOrDefault(args[0], defaultCommand);
        
        if ((command.minArgs() > args.length) || (command.maxArgs() < args.length)) {
            throw new Exception(command.name() + ": Wrong number of arguments");
        }
        try {
            command.execute(state, args);
        } catch (Exception e) {
            throw new Exception(command.name() + ": " + e.getMessage());
        }
    }
}


