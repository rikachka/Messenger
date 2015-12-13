package project.server;

import project.server.commands.Command;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Interpreter {
    private CommandHandlerServer commandHandlerServer = new CommandHandlerServer();
    BufferedReader in;
    PrintWriter out;
    
    public Interpreter(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
    
    public void addCommand(Command command) {
        commandHandlerServer.addCommand(command);
    }
    
    public void interactiveMode(Session session) {
        Scanner inScanner = new Scanner(in);

        try {
            commandHandlerServer.startCommand(session, "start");
        } catch (Exception e) {
            out.println(e.getMessage());
        }

        while (true) {
            out.print("$ ");
            out.flush();
            String input;
            if (inScanner.hasNextLine()) {
                input = inScanner.nextLine();
            } else {
                break;
            }
            try {
                commandHandlerServer.startCommand(session, input);
//            } catch (DataBaseException e) {
//                out.println(e.getMessage());
//                System.exit(1);
//            } catch (ThreadInterruptException e) {
//                return;
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }
}

