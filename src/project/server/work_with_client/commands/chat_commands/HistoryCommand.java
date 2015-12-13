package project.server.work_with_client.commands.chat_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

import java.util.List;

/**
 * Created by rikachka on 07.11.15.
 */
public class HistoryCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }

        Chat chat = session.getUserChat(args[0]);
        if (chat == null) {
            return;
        }

        List<String> messages = session.chats().getMessagesTexts(chat);
        if (messages.size() == 0) {
            writeln("no messages were found");
        } else {
            for (String message : messages) {
                writeln(message);
            }
        }
        writeToClient(session);
    }

    public String name() { return "chat_history"; }

    public int minArgs() { return 1; }

    public int maxArgs() { return 1; }

    public String help() { return "<chat_id>"; }

    public String example() { return "1"; }
}
