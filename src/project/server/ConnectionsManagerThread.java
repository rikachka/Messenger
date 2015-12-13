package project.server;

import project.server.work_with_client.ClientAcceptorThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionsManagerThread extends Thread {
    ServerSocket serverSocket;
    SessionManager sessionManager;
    private List<ClientAcceptorThread> clientAcceptors = new ArrayList<>();

    public ConnectionsManagerThread(ServerSocket serverSocket, SessionManager sessionManager) {
        this.serverSocket = serverSocket;
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    serverSocket.setSoTimeout(100);
                    Socket clientSocket = serverSocket.accept();
                    ClientAcceptorThread clientAcceptorThread = new ClientAcceptorThread(clientSocket, sessionManager);
                    clientAcceptors.add(clientAcceptorThread);
                    clientAcceptorThread.start();
                } catch (SocketTimeoutException e) {
                    // just to check whether it was interrupted
                }
            }
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }
    }

    public List<ClientAcceptorThread> getClientAcceptors() {
        return clientAcceptors;
    }
}
