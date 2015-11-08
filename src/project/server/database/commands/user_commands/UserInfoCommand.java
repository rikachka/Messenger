package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.User;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class UserInfoCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }
        User user = null;
        if (args.length == 1) {
            user = state.getUser();
            state.out.println(user.getId() + " " + user.getLogin() + " " + user.getPassword() + " " + user.getNickname());
        } else {
            try {
                Long id = new Long(args[1]);
                user = state.getUser(id);
                if (user == null) {
                    state.out.println("no such user");
                } else {
                    state.out.println(user.getId() + " " + user.getLogin() + " " + user.getNickname());
                }
            } catch (NumberFormatException e) {
                state.out.println("wrong format of id");
                return;
            }
        }
    }

    public String name() { return "/user_info"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 2; }

    public String help() { return "<user_id> (your own by default)"; }

    public String example() { return "1"; }
}