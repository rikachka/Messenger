package project.server.commands.server_commands;

import project.server.Session;
import project.server.commands.Command;

public class StopCommand implements Command {
    public void execute(Session session, String[] args) throws Exception {
        if (!session.isStarted()) {
            session.out.println("not started");
            return;
        }

        int port = session.stop();
        session.out.println("stopped at port " + port);
    }
    
    public String name() {
        return "stop";
    }
    
    public int minArgs() {
        return 1;
    }
    
    public int maxArgs() {
        return 1;
    }
}
