package project.server.database.commands.chat_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.Chat;
import project.server.database.classes.Message;
import project.server.database.classes.User;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.util.List;

/**
 * Created by rikachka on 07.11.15.
 */
public class SendCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }

        Chat chat = state.getUserChat(args[1]);
        if (chat == null) {
            return;
        }

        String text = args[2];
        Message message = chat.addMessage(state.getUser().getId(), text);
        state.getSessionManager().sendMessage(chat, message);
        state.out.println("the message was sent");
    }

    public String name() { return "/chat_send"; }

    public int minArgs() { return 3; }

    public int maxArgs() { return 3; }

    public String help() { return "<chat_id> <message"; }

    public String example() { return "1 Hello!"; }
}
