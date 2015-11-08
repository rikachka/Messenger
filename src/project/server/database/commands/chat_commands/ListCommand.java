package project.server.database.commands.chat_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.util.Set;

/**
 * Created by rikachka on 07.11.15.
 */
public class ListCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }
        Set<Long> userChats = state.chats().getChats(state.getUser());
        if (userChats.size() == 0) {
            state.out.println("no chats were found");
        } else {
            for (Long chatId : userChats) {
                state.out.print(chatId + " ");
            }
            state.out.println();
        }
    }

    public String name() { return "/chat_list"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return ""; }

    public String example() { return ""; }
}