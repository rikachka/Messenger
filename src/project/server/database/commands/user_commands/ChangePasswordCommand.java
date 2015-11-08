package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.User;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class ChangePasswordCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }
        String oldPassword = args[1];
        String newPassword = args[2];
        User user = state.getUser();
        if (!user.getPassword().equals(oldPassword)) {
            state.out.println("wrong password");
        } else {
            user.setPassword(newPassword);
            state.out.println("password was changed");
        }
    }

    public String name() { return "/user_pass"; }

    public int minArgs() { return 3; }

    public int maxArgs() { return 3; }

    public String help() { return "<old_password> <new_password>"; }

    public String example() { return "123 qwe"; }
}
