package project.server.commands.server_commands;

import project.server.InterpreterStateServer;
import project.server.commands.Command;

public class ExitCommand implements Command {
    public void execute(InterpreterStateServer state, String[] args) throws Exception {
        if (state.isStarted()) {
            state.stop();
        }
        System.exit(0);
    }
    
    public String name() {
        return "exit";
    }
    
    public int minArgs() {
        return 1;
    }
    
    public int maxArgs() {
        return 1;
    }
}
