package project.server.commands;

import project.server.Session;

public interface Command {
    void execute(Session session, String[] args) throws Exception;
    
    String name();
    
    int minArgs();
    
    int maxArgs();
}

