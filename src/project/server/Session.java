package project.server;

import project.server.work_with_client.ClientAcceptorThread;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.List;

public class Session {
    private SessionManager sessionManager;
    public PrintWriter out;
    private boolean started;
    private ServerSocket serverSocket;
    private int port;
    private ConnectionsManagerThread connectionsManager;

    public Session(SessionManager sessionManager, PrintWriter out) {
        this.out = out;
        this.sessionManager = sessionManager;
    }

    public void start(int port, ServerSocket serverSocket) {
        this.port = port;
        this.serverSocket = serverSocket;
        started = true;
        connectionsManager = new ConnectionsManagerThread(serverSocket, sessionManager);
        connectionsManager.start();
    }

    public int stop() throws Exception {
        started = false;
        connectionsManager.interrupt();
        try {
            connectionsManager.join();
            serverSocket.close();
        } catch (Exception e) {
            throw new Exception("Error while stopping the server");
        }
        return port;
    }

    public boolean isStarted() {
        return started;
    }

    public int getPort() {
        return port;
    }

    public List<ClientAcceptorThread> getClientAcceptors() {
        return connectionsManager.getClientAcceptors();
    }
}
