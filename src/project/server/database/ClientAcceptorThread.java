package project.server.database;

import project.server.SessionManager;
import project.server.database.commands.DatabaseCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            InterpreterDatabase interpreter = new InterpreterDatabase(in, out);
            Set<DatabaseCommand> commands = interpreter.getCommands();
            InterpreterStateDatabase state = new InterpreterStateDatabase(sessionManager, out, commands);
            interpreter.interactiveMode(state);
            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Exception in thread");
        }
    }
}
