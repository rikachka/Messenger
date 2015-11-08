package project.server.database.commands.chat_commands;

import project.server.database.InterpreterStateDatabase;
import project.server.database.classes.Chat;
import project.server.database.commands.DatabaseCommand;
import project.server.database.utils.Utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rikachka on 07.11.15.
 */
public class CreateCommand implements DatabaseCommand {
    public void execute(InterpreterStateDatabase state, String[] args) throws Exception {
        if (!Utils.checkUserAuthorised(state)) {
            return;
        }

        Set<Long> participants = new HashSet<>();
        try {
            participants.add(state.getUser().getId());
            for (String id : args[1].split(",")) {
                Long userId = new Long(id);
                participants.add(userId);
                if (!Utils.checkUserExisting(state, userId)) {
                    return;
                }
            }
        } catch (Exception e) {
            state.out.println("wrong format of arguments");
            return;
        }
        if (participants.size() < 2) {
            state.out.println("too few participants of the chat");
            return;
        }
        Chat chat = state.chats().createChat(participants, state.getUser());
        state.out.println("welcome to the chat " + chat.getId());
        state.getSessionManager().sendChatWelcome(chat, state.getUser().getId());
    }

    public String name() { return "/chat_create"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<participants_id>"; }

    public String example() { return "1,2,3"; }
}
