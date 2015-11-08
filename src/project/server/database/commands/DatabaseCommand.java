package project.server.database.commands;

import project.server.database.InterpreterStateDatabase;

public interface DatabaseCommand {
    void execute(InterpreterStateDatabase state, String[] args) throws Exception;

    String name();

    int minArgs();

    int maxArgs();

    String help();

    String example();
}

