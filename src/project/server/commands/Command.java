package project.server.commands;

import project.server.InterpreterStateServer;

public interface Command {
    void execute(InterpreterStateServer state, String[] args) throws Exception;
    
    String name();
    
    int minArgs();
    
    int maxArgs();
}

