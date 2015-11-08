package project.server;

import project.server.database.ClientAcceptorThread;
import project.server.database.file_system.SaveDatabase;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.List;

public class InterpreterStateServer {
    private SessionManager sessionManager;
    public PrintWriter out;
    private boolean started;
    private ServerSocket serverSocket;
    private int port;
    private ConnectionsManagerThread connectionsManager;

    public InterpreterStateServer(SessionManager sessionManager, PrintWriter out) {
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
            SaveDatabase.start(sessionManager);
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
