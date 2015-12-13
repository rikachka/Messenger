package project.server.work_with_client.commands.chat_commands;

import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.classes.Chat;
import project.server.work_with_client.classes.ChatMessage;
import project.server.work_with_client.commands.ClientCommandAbstract;
import project.server.work_with_client.utils.Utils;

/**
 * Created by rikachka on 07.11.15.
 */
public class SendCommand extends ClientCommandAbstract {
    public void run(SessionWithClient session, String[] args) throws Exception {
        beforeRunning();
        if (!Utils.checkUserAuthorised(session)) {
            return;
        }

        Chat chat = session.getUserChat(args[0]);
        if (chat == null) {
            return;
        }

        String text = args[1];
        ChatMessage chatMessage = session.chats().addMessage(chat, session.getUser().getId(), text);
        session.getSessionManager().sendMessage(chat, chatMessage);
        //writeln("the message was sent");
        writeToClient(session);
    }

    public String name() { return "chat_send"; }

    public int minArgs() { return 2; }

    public int maxArgs() { return 2; }

    public String help() { return "<chat_id> <message"; }

    public String example() { return "1 Hello!"; }
}
