package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.commands.ClientCommandMy;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class RegistrateCommand extends ClientCommandMy {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserNotAuthorised(session)) {
            return;
        }
        if (args.length == 2) {
            (new LoginCommand()).run(session, args);
            return;
        }
        String login = args[0];
        session.setUser(login);
        if (session.getUser() == null) {
            session.writeErrorToClient("such login is already taken");
        } else {
            writeln("registration completed");
        }
        writeToClient(session);
    }

    public String name() { return "login"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 2; }

    public String help() { return "<login>"; }

    public String example() { return "user"; }
}
