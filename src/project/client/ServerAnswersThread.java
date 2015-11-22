package project.client;

import project.common.messages.Message;
import project.common.messages.MessageType;
import project.common.messages.SocketStream;
import project.server.SessionManager;
import project.server.work_with_client.InterpreterClient;
import project.server.work_with_client.SessionWithClient;
import project.server.work_with_client.commands.ClientCommand;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

public class ServerAnswersThread extends Thread {
    private Session session;

    public ServerAnswersThread(Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (session.socketStream.available() > 0) {
                    Message answer = session.socketStream.read();
                    if (!answer.getContent().isEmpty()) {
                        if (answer.getType() == MessageType.ERROR) {
                            session.err.println(answer.getContent());
                            session.err.flush();
                        } else {
                            session.out.println(answer.getContent());
                            session.out.flush();
                        }
                    }
                }
            } catch (IOException e) {
                session.err.println("Server diconnected");
            } catch (ClassNotFoundException e) {
                session.err.println("Client can't work with such messages");
            }
        }

    }
}
