package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class RegistrateCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserNotAuthorised(state)) {
            return;
        }
        if (args.length == 3) {
            (new LoginCommand()).execute(state, args);
            return;
        }
        String login = args[1];
        state.setUser(login);
        if (state.getUser() == null) {
            state.out.println("such login is already taken");
        } else {
            state.out.println("registration completed");
        }
    }

    public String name() { return "/login"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 3; }

    public String help() { return "<login>"; }

    public String example() { return "user"; }
}
