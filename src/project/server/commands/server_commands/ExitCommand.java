package project.server.commands.server_commands;

import project.server.Session;
import project.server.commands.Command;

public class ExitCommand implements Command {
    public void execute(Session session, String[] args) throws Exception {
        if (session.isStarted()) {
            session.stop();
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
