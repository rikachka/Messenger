package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.User;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class ChangePasswordCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }
        String oldPassword = args[0];
        String newPassword = args[1];
        User user = session.getUser();
        if (!user.getPassword().equals(oldPassword)) {
            session.writeErrorToClient("wrong password");
        } else {
            user.setPassword(newPassword);
            session.users().updateUser(user);
            writeln("password was changed");
        }
        writeToClient(session);
    }

    public String name() { return "user_pass"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<old_password> <new_password>"; }

    public String example() { return "123 qwe"; }
}
