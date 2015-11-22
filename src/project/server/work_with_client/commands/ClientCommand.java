package project.server.work_with_client.commands;

import project.server.work_with_client.SessionWithClient;

public interface ClientCommand {
    void execute(SessionWithClient session, String[] args) throws Exception;

    String name();

    int minArgs();

    int maxArgs();

    String help();

    String example();
}

