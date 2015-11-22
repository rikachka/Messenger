package project.common.messages;

import project.common.messages.Message;

import java.io.*;

/**
 * Created by rikachka on 20.11.15.
 */
public class SocketStream {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Protocol protocol;

    public SocketStream(InputStream in, OutputStream out) throws IOException {
        this.out = new ObjectOutputStream(out);
        this.in = new ObjectInputStream(in);
        protocol = new SerializationProtocol();
    }

    public int available() throws IOException {
        try {
            return in.available();
        } catch (Exception e) {
            System.err.println("Can't find out, whether it is available to read from the socket");
            throw e;
        }
    }

    public Message read() throws IOException, ClassNotFoundException {
        try {
            int bytesLength = in.readInt();
            byte[] bytes = new byte[bytesLength];
            in.readFully(bytes);
            Message message = protocol.decode(bytes);
            return message;
        } catch (Exception e) {
            System.err.println("Can't read from the socket");
            throw e;
        }
    }

    public void write(Message message) {
        try {
            byte[] bytes = protocol.encode(message);
            int bytesLength = bytes.length;
            out.writeInt(bytesLength);
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            System.err.println("Can't write to the socket");
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
}
