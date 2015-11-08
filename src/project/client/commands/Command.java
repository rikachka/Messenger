package project.client.commands;

import project.client.InterpreterState;

public interface Command {
    void execute(InterpreterState state, String[] args) throws Exception;
    
    String name();
    
    int minArgs();
    
    int maxArgs();
}

