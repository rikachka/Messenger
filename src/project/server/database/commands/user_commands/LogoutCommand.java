package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class LogoutCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }
        state.clearUser();
        state.out.println("you are logged out");
    }

    public String name() { return "/logout"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return ""; }

    public String example() { return ""; }
}
