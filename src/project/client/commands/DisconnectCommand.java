package project.client.commands;

import project.client.InterpreterState;

public class DisconnectCommand implements Command {
    public void execute(InterpreterState state, String[] args) throws Exception {
        if (!state.isConnected()) {
            state.out.println("not connected");
            return;
        }
        state.disconnect();
        state.out.println("disconnected");
    }
    
    public String name() {
        return "disconnect";
    }
    
    public int minArgs() {
        return 1;
    }
    
    public int maxArgs() {
        return 1;
    }
}
