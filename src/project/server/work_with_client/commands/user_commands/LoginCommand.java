package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class LoginCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserNotAuthorised(session)) {
            return;
        }
        if (args.length == 1) {
            (new RegistrateCommand()).run(session, args);
            return;
        }
        String login = args[0];
        String password = args[1];
        session.setUser(login, password);
        if (session.getUser() == null) {
            session.writeErrorToClient("wrong login or password");
        } else {
            writeln("authorization completed");
        }
        writeToClient(session);
    }

    public String name() { return "login"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 2; }

    public String help() { return "<login> <password>"; }

    public String example() { return "user 123"; }
}
