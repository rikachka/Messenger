package project.client.commands;

import project.client.Session;

public class ExitCommand implements Command {
    public void execute(Session session, String[] args) throws Exception {
        session.disconnect();
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
