package project.server.work_with_client.commands;

import project.server.work_with_client.SessionWithClient;

/**
 * Created by rikachka on 20.11.15.
 */
public abstract class ClientCommandAbstract implements ClientCommand {
    protected String answer = "";
    //protected SessionWithClient session;

    public void execute(SessionWithClient session, String[] args) throws Exception {
        //this.session = session;
        run(session, args);
    }

    protected void write(String str) { answer += str; }

    protected void writeln(String str) {
        answer += str + "\n";
    }

    protected void writeln() {
        writeln("");
    }

    protected void writeToClient(SessionWithClient session) {
        if (!answer.equals("")) {
            session.writeToClient(name(), answer);
        }
    }

    protected void beforeRunning() {
        answer = "";
    }

    abstract public void run(SessionWithClient session, String[] args) throws Exception;

    abstract public String name();

    abstract public int minArgs();

    abstract public int maxArgs();

    abstract public String help();

    abstract public String example();
}
