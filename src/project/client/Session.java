package project.client;

import project.common.messages.SocketStream;
import project.server.work_with_client.ClientAcceptorThread;

import java.io.*;
import java.net.Socket;


// Добавьте коментарии к классу, что он делает
public class Session {
    public PrintWriter out;
    public PrintWriter err;
    private Socket socket;
    public SocketStream socketStream;
    private ServerAnswersThread serverAnswersThread;

    public Session(PrintWriter out, PrintWriter err) {
        this.out = out;
        this.err = err;
    }

    public void connect(String host, int port) throws Exception {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new Exception("Server is not listening at " + host + ":" + port);
        }
        try {
            socketStream = new SocketStream(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            throw new Exception("Can't create streams between client and server");
        }
        serverAnswersThread = new ServerAnswersThread(this);
        serverAnswersThread.start();
    }

    public void disconnect() throws Exception {
        try {
            //toServerStream.println("exit");
            serverAnswersThread.interrupt();
            serverAnswersThread.join();
            socketStream.close();
//            fromServerStream.close();
//            toServerStream.close();
            socket.close();
        } catch (IOException e) {
            throw new Exception("Can't disconnect");
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
