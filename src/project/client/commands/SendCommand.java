package project.client.commands;

import project.client.InterpreterState;
import project.client.Utils;

import java.io.IOException;

public class SendCommand implements Command {
    public void execute(InterpreterState state, String[] args) throws Exception {
        if (!state.isConnected()) {
            state.out.println("not connected");
            return;
        }
        if (state.getSocket().isInputShutdown()) {
            state.disconnect();
            state.out.println("Server disconnected. Try to connect again.");
        }
        String commandToServer = Utils.concatStrings(args, " ");
        state.toServerStream.println(commandToServer);
        try {
            Thread.sleep(100);
            do {
                String answerFromServer = state.fromServerStream.readLine();
                state.out.println(answerFromServer);
            } while (state.fromServerStream.ready());
        } catch (IOException e) {
            state.disconnect();
            state.out.println("Server disconnected. Try to connect again.");
        }
    }
    
    public String name() {
        return "send";
    }
    
    public int minArgs() { //name of the command is not given as arg[0]
        return 0;
    }
    
    public int maxArgs() {
        return Integer.MAX_VALUE;
    }
}
