package project.server;

import project.server.commands.Command;

import java.util.HashMap;


//TODO: можно же использовать тот, что в клиенте. Это дублирование кода
public class CommandHandlerServer {
    
    private HashMap<String, Command> allCommands = new HashMap<>();
    
    public void addCommand(Command command) {
        allCommands.put(command.name(), command);
    }

    public void startCommand(Session session, String commandWithArgs) throws Exception {
        String[] args = commandWithArgs.trim().split("\\s+");
        if (args[0].isEmpty()) {
            return;
        }
        if (!allCommands.containsKey(args[0])) {
            throw new Exception("No such command");
        }
        Command command = allCommands.get(args[0]);
        
        if ((command.minArgs() > args.length) || (command.maxArgs() < args.length)) {
            throw new Exception(command.name() + ": Wrong number of arguments");
        }
        try {
            command.execute(session, args);
        } catch (Exception e) {
            throw new Exception(command.name() + ": " + e.getMessage());
        }
    }
}


