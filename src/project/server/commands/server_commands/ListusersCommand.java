package project.server.commands.server_commands;

import project.server.InterpreterStateServer;
import project.server.commands.Command;
import project.server.database.ClientAcceptorThread;

import java.util.List;

public class ListusersCommand implements Command {
    public void execute(InterpreterStateServer state, String[] args) {
        if (!state.isStarted()) {
            state.out.println("not started");
            return;
        }
        int port = state.getPort();
        List<ClientAcceptorThread> clientAcceptors = state.getClientAcceptors();
        for (ClientAcceptorThread clientAcceptor: clientAcceptors) {
            if  (clientAcceptor.isAlive()) {
                String host = clientAcceptor.clientSocket.getInetAddress().getHostName();
                state.out.println(host + ":" + port);
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
