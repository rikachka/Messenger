package project.server.commands.server_commands;

import project.server.Session;
import project.server.commands.Command;
import project.server.work_with_client.ClientAcceptorThread;

import java.util.List;

public class ListusersCommand implements Command {
    public void execute(Session session, String[] args) {
        if (!session.isStarted()) {
            session.out.println("not started");
            return;
        }
        int port = session.getPort();
        List<ClientAcceptorThread> clientAcceptors = session.getClientAcceptors();
        for (ClientAcceptorThread clientAcceptor: clientAcceptors) {
            if  (clientAcceptor.isAlive()) {
                String host = clientAcceptor.clientSocket.getInetAddress().getHostName();
                session.out.println(host + ":" + port);
            }
        }
    }
    
    public String name() {
        return "listusers";
    }
    
    public int minArgs() {
        return 1;
    }
    
    public int maxArgs() {
        return 1;
    }
}
