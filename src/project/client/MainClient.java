package project.client;

import project.client.commands.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainClient {
    public static void main(String[] args) {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out, true);

        Interpreter interpreter = new Interpreter(in, out);
        interpreter.setDefaultCommand(new SendCommand());
        interpreter.addCommand(new ConnectCommand());
        interpreter.addCommand(new DisconnectCommand());
        interpreter.addCommand(new WhereamiCommand());
        interpreter.addCommand(new ExitCommand());

        InterpreterState state = new InterpreterState(out);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (state.isConnected()) {
                    try {
                        state.disconnect();
                        out.close();
                        in.close();
                    } catch (Exception e) {
                        System.err.println("Can't stop the client");
                    }
                }
            }
        });

        if (args.length == 0) {
            interpreter.interactiveMode(state);
        } else {
            String wholeArgument = Utils.concatStrings(args, " ");
            try {
                interpreter.batchMode(state, wholeArgument);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
