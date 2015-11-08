package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class LoginCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserNotAuthorised(state)) {
            return;
        }
        if (args.length == 2) {
            (new RegistrateCommand()).execute(state, args);
            return;
        }
        String login = args[1];
        String password = args[2];
        state.setUser(login, password);
        if (state.getUser() == null) {
            state.out.println("wrong login or password");
        } else {
            state.out.println("authorization completed");
        }
    }

    public String name() { return "/login"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 3; }

    public String help() { return "<login> <password>"; }

    public String example() { return "user 123"; }
}
