package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.User;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class UserInfoCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }
        User user = null;
        if (args.length == 0) {
            user = session.getUser();
            writeln(user.getId() + " " + user.getLogin() + " " + user.getPassword() + " " + user.getNickname());
        } else {
            try {
                Long id = new Long(args[0]);
                user = session.getUser(id);
                if (user == null) {
                    session.writeErrorToClient("no such user");
                } else {
                    writeln(user.getId() + " " + user.getLogin() + " " + user.getNickname());
                }
            } catch (NumberFormatException e) {
                session.writeErrorToClient("wrong format of id");
                return;
            }
        }
        writeToClient(session);
    }

    public String name() { return "user_info"; }

    public int minArgs() { return 0; }

    public int maxArgs() { return 1; }

    public String help() { return "<user_id> (your own by default)"; }

    public String example() { return "1"; }
}