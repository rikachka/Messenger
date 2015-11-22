package project.client.commands;

import project.client.Session;

public interface Command {
    void execute(Session session, String[] args) throws Exception;
    
    String name();
    
    int minArgs();
    
    int maxArgs();
}

