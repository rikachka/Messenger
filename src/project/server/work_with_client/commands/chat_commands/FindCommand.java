package project.server.work_with_client.commands.chat_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.commands.ClientCommand;
import project.server.work_with_client.commands.ClientCommandMy;
import project.server.work_with_client.utils.Utils;

import java.util.List;

/**
 * Created by rikachka on 07.11.15.
 */
public class FindCommand extends ClientCommandMy {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }

        Chat chat = session.getUserChat(args[0]);
        if (chat == null) {
            return;
        }

        String searching = args[1];
        List<String> messages = chat.getMessagesTexts(searching);
        if (messages.size() == 0) {
            writeln("no messages were found");
        } else {
            for (String message : messages) {
                writeln(message);
            }
        }
        writeToClient(session);
    }

    public String name() { return "chat_find"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<word>"; }

    public String example() { return "pizza"; }
}
