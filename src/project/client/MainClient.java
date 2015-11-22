package project.client;

import project.client.commands.*;
import project.client.exceptions.NotCriticalException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 10001;

    public static void main(String[] args) {
        Scanner inScanner  = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out, true);
        PrintWriter err = new PrintWriter(System.err, true);

        CommandHandler commandHandler = new CommandHandler();
        addCommands(commandHandler);

        Session session = new Session(out, err);
        try {
            session.connect(HOST, PORT);
        } catch (Exception e) {
            err.println(e.getMessage());
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    session.disconnect();
                    out.close();
                    inScanner.close();
                } catch (Exception e) {
                    System.err.println("Can't stop the client");
                }
            }
        });

        //out.print("$ ");
        //out.flush();
        out.println("Welcome to the chat!");
        while (inScanner.hasNextLine()) {
            String input;
            input = inScanner.nextLine();
            try {
                commandHandler.startCommand(session, input);
            } catch (NotCriticalException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            //out.print("$ ");
            //out.flush();
        }
    }

    private static void addCommands(CommandHandler commandHandler) {
        commandHandler.setDefaultCommand(new SendCommand());
        commandHandler.addCommand(new ExitCommand());
    }
}
