package project.client.commands;

import project.client.Session;
import project.client.exceptions.CriticalException;
import project.client.exceptions.NotCriticalException;

public interface Command {
    void execute(Session session, String[] args) throws CriticalException, NotCriticalException;
    
    String name();
    
    int minArgs();
    
    int maxArgs();
}

