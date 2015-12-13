package project.server.work_with_client.commands.chat_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

import java.util.Set;

/**
 * Created by rikachka on 07.11.15.
 */
public class ListCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }
        Set<Long> userChats = session.chats().getChats(session.getUser());
        if (userChats.size() == 0) {
            writeln("no chats were found");
        } else {
            for (Long chatId : userChats) {
                write(chatId + " ");
            }
            writeln();
        }
        writeToClient(session);
    }

    public String name() { return "chat_list"; }

    public int minArgs() { return 0; }

    public int maxArgs() { return 0; }

    public String help() { return ""; }

    public String example() { return ""; }
}