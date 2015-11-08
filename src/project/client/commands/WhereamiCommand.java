package project.client.commands;

import project.client.InterpreterState;

public class WhereamiCommand implements Command {
    public void execute(InterpreterState state, String[] args) {
        if (!state.isConnected()) {
            state.out.println("local");
        } else {
            state.out.println("remote " + state.getHost() + " " + state.getPort());
        }
    }
    
    public String name() {
        return "whereami";
    }
    
    public int minArgs() {
        return 1;
    }
    
    public int maxArgs() {
        return 1;
    }
}
