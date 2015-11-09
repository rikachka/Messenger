package project.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


// Добавьте коментарии к классу, что он делает

// TODO: по смыслу это больше похоже на сессию, может назвать класс Session?
public class InterpreterState {
    public PrintWriter out;
    private boolean connected = false;
    private String host;
    private int port;
    private Socket socket;
    public BufferedReader fromServerStream;
    public PrintWriter toServerStream;

    public InterpreterState(PrintWriter out) {
        this.out = out;
    }

    public void connect(String host, int port, Socket socket) throws Exception {
        try {
            this.host = host;
            this.port = port;
            this.socket = socket;
            connected = true;
            fromServerStream  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServerStream = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new Exception("Can't create streams between client and server");
        }
    }

    public void disconnect() throws Exception {
        try {
            toServerStream.println("exit");

            // FIXME: null-checking
            fromServerStream.close();
            toServerStream.close();
            socket.close();
            connected = false;
        } catch (IOException e) {
            throw new Exception("Can't disconnect");
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }
}
