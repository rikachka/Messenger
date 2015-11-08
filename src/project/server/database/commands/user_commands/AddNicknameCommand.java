package project.server.database.commands.user_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class AddNicknameCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }
        String nickname = args[1];
        state.getUser().setNickname(nickname);
        state.out.println("nickname was changed");
    }

    public String name() { return "/user"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<new_nickname>"; }

    public String example() { return "captain"; }
}