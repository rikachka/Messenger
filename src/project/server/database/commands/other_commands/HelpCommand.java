package project.server.database.commands.other_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

/**
 * Created by rikachka on 08.11.15.
 */
public class HelpCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        state.printCommandsHelp();
    }

    public String name() { return "/help"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return ""; }

    public String example() { return ""; }
}
