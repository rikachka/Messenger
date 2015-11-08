package project.server.database.commands.chat_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.Chat;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.util.List;

/**
 * Created by rikachka on 07.11.15.
 */
public class HistoryCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }

        Chat chat = state.getUserChat(args[1]);
        if (chat == null) {
            return;
        }

        List<String> messages = chat.getMessagesTexts();
        if (messages.size() == 0) {
            state.out.println("no messages were found");
        } else {
            for (String message : messages) {
                state.out.println(message);
            }
        }
    }

    public String name() { return "/chat_history"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<chat_id>"; }

    public String example() { return "1"; }
}
