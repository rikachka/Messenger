package project.server.work_with_client.commands.other_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommandAbstract;

/**
 * Created by rikachka on 08.11.15.
 */
public class HelpCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        write(session.printCommandsHelp());
        writeToClient(session);
    }

    public String name() { return "help"; }

    public int minArgs() { return 0; }

    public int maxArgs() { return 0; }

    public String help() { return ""; }

    public String example() { return ""; }
}
