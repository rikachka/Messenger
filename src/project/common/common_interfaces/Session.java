package project.common.common_interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


// Добавьте коментарии к классу, что он делает

// TODO: по смыслу это больше похоже на сессию, может назвать класс Session?
public class Session {
    public PrintWriter out;
    private String host;
    private int port;

    public Session(PrintWriter out) {
        this.out = out;
    }

    public void connect(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
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
