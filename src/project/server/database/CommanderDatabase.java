package project.server.database;

import project.server.database.commands.DatabaseCommand;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommanderDatabase {
    
    private HashMap<String, DatabaseCommand> allCommands = new HashMap<>();
    
    public void addCommand(DatabaseCommand command) {
        allCommands.put(command.name(), command);
    }

    public void startCommand(InterpreterStateDatabase state, String commandWithArgs) throws Exception {
        String[] args;
        if (commandWithArgs.trim().startsWith("/chat_send ")) {
            args = commandWithArgs.trim().split("\\s+", 3);
        } else {
            args = commandWithArgs.trim().split("\\s+");
        }
        if (args[0].isEmpty()) {
            return;
        }
        if (!allCommands.containsKey(args[0])) {
            throw new Exception("No such command");
        }
        DatabaseCommand command = allCommands.get(args[0]);
        
        if ((command.minArgs() > args.length) || (command.maxArgs() < args.length)) {
            throw new Exception(command.name() + ": Wrong number of arguments");
        }
        try {
            command.execute(state, args);
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

    public Set<DatabaseCommand> getCommands() {
        Set<DatabaseCommand> commands = new HashSet<>();
        commands.addAll(allCommands.values());
        return commands;
    }
}


