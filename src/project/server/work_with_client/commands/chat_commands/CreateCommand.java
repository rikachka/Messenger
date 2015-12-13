package project.server.work_with_client.commands.chat_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rikachka on 07.11.15.
 */
public class CreateCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }

        Set<Long> participants = new HashSet<>();
        try {
            participants.add(session.getUser().getId());
            for (String id : args[0].split(",")) {
                Long userId = new Long(id);
                participants.add(userId);
                if (!Utils.checkUserExisting(session, userId)) {
                    return;
                }
            }
        } catch (Exception e) {
            session.writeErrorToClient("wrong format of arguments");
            return;
        }
        if (participants.size() < 2) {
            session.writeErrorToClient("too few participants of the chat");
            return;
        }
        Chat chat = session.chats().createChat(participants, session.getUser());
        writeln("welcome to the chat " + chat.getId());
        session.getSessionManager().sendChatWelcome(chat, session.getUser().getId());
        writeToClient(session);
    }

    public String name() { return "chat_create"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return "<participants_id>"; }

    public String example() { return "1,2,3"; }
}
