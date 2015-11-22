package project.server.work_with_client;

import project.common.messages.SocketStream;
import project.server.SessionManager;
import project.server.work_with_client.commands.ClientCommand;

import java.net.Socket;
import java.util.Set;

public class ClientAcceptorThread extends Thread {
    public Socket clientSocket;
    SessionManager sessionManager;

    public ClientAcceptorThread(Socket socket, SessionManager sessionManager) {
        clientSocket = socket;
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        try {
            SocketStream socketStream = new SocketStream(clientSocket.getInputStream(), clientSocket.getOutputStream());
            InterpreterClient interpreter = new InterpreterClient(socketStream, sessionManager.getCommandHandler());
            Set<ClientCommand> commands = interpreter.getCommands();
            SessionWithClient session = new SessionWithClient(sessionManager, socketStream, commands);
            //System.out.println("Client accepted");
            interpreter.interactiveMode(session);

            // FIXME: close resources in finally
//            out.close();
//            in.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Exception in thread");
        }
    }
}
