package project.server.work_with_client.commands.user_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class AddNicknameCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }
        String nickname = args[0];
        session.getUser().setNickname(nickname);
        session.users().updateUser(session.getUser());
        writeln("nickname was changed");
        writeToClient(session);
    }

    public String name() { return "user"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return "<new_nickname>"; }

    public String example() { return "captain"; }
}