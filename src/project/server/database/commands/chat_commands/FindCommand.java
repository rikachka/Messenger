package project.server.database.commands.chat_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.Chat;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.util.List;

/**
 * Created by rikachka on 07.11.15.
 */
public class FindCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }

        Chat chat = state.getUserChat(args[1]);
        if (chat == null) {
            return;
        }

        String searching = args[2];
        List<String> messages = chat.getMessagesTexts(searching);
        if (messages.size() == 0) {
            state.out.println("no messages were found");
        } else {
            for (String message : messages) {
                state.out.println(message);
            }
        }
    }

    public String name() { return "/chat_find"; }

    public int minArgs() { return 3; }

    public int maxArgs() { return 3; }

    public String help() { return "<word>"; }

    public String example() { return "pizza"; }
}
