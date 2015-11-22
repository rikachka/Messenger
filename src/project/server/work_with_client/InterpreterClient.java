package project.server.work_with_client;

import project.common.messages.Message;
import project.common.messages.SocketStream;
import project.server.CommandHandlerClient;
import project.server.work_with_client.commands.ClientCommand;

import java.io.*;
import java.util.Set;

public class InterpreterClient {
    private CommandHandlerClient commandHandler;
    SocketStream socketStream;

    public InterpreterClient(SocketStream socketStream, CommandHandlerClient commandHandler) {
        this.socketStream = socketStream;
        this.commandHandler = commandHandler;
    }
    
    public void interactiveMode(SessionWithClient session) {
        while (true) {
            Message message = null;
            try {
                while (!(socketStream.available() > 0)) {
                    if (Thread.currentThread().isInterrupted()) {
                        socketStream.close();
                        return;
                    }
                }
                message = socketStream.read();
            } catch (IOException e) {
                return;
            } catch (ClassNotFoundException e) {
                session.writeErrorToClient("Server can't work with such messages");
            }
            try {
                commandHandler.startCommand(session, message);
            } catch (Exception e) {
                session.writeErrorToClient(e.getMessage());
            }
        }
    }

    public Set<ClientCommand> getCommands() {
        return commandHandler.getCommands();
    }
}

