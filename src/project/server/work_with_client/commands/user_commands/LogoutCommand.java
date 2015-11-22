package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.commands.ClientCommandMy;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class LogoutCommand extends ClientCommandMy {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }
        session.clearUser();
        writeln("you are logged out");
        writeToClient(session);
    }

    public String name() { return "logout"; }

    public int minArgs() { return 0; }

    public int maxArgs() { return 0; }

    public String help() { return ""; }

    public String example() { return ""; }
}
