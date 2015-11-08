package project.server;

import project.server.commands.Command;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Interpreter {
    private Commander commander = new Commander();
    BufferedReader in;
    PrintWriter out;
    
    public Interpreter(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
    
    public void addCommand(Command command) {
        commander.addCommand(command);
    }
    
    public void interactiveMode(InterpreterStateServer state) {
        Scanner inScanner = new Scanner(in);

        try {
            commander.startCommand(state, "start");
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
                batchMode(state, input);
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
    
    public void batchMode(InterpreterStateServer state, String arg) throws Exception {
        String[] commands = arg.split(";");
        for (String command : commands) {
            commander.startCommand(state, command);
        }
    }
}

