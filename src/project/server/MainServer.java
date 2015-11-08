package project.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import project.server.commands.server_commands.*;

public class MainServer {

    public static void main(String[] args) {

        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out, true);

        Interpreter interpreter = new Interpreter(in, out);
        interpreter.addCommand(new StartCommand());
        interpreter.addCommand(new StopCommand());
        interpreter.addCommand(new ListusersCommand());
        interpreter.addCommand(new ExitCommand());

        SessionManager sessionManager = null;
        try {
            sessionManager = new SessionManager();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        InterpreterStateServer state = new InterpreterStateServer(sessionManager, out);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (state.isStarted()) {
                    try {
                        state.stop();
                        out.close();
                        in.close();
                    } catch (Exception e) {
                        System.err.println("Can't stop the server");
                    }
                }
            }
        });

        interpreter.interactiveMode(state);
    }
}
