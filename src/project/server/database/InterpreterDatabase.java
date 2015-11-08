package project.server.database;

import project.server.database.commands.DatabaseCommand;
import project.server.database.commands.chat_commands.*;
import project.server.database.commands.other_commands.HelpCommand;
import project.server.database.commands.user_commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class InterpreterDatabase {
    private CommanderDatabase commander = new CommanderDatabase();
    BufferedReader in;
    PrintWriter out;
    
    public InterpreterDatabase(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        addCommand(new RegistrateCommand());
        addCommand(new LoginCommand());
        addCommand(new LogoutCommand());
        addCommand(new AddNicknameCommand());
        addCommand(new UserInfoCommand());
        addCommand(new ChangePasswordCommand());

        addCommand(new CreateCommand());
        addCommand(new FindCommand());
        addCommand(new HistoryCommand());
        addCommand(new ListCommand());
        addCommand(new SendCommand());

        addCommand(new HelpCommand());
//        addCommand(new DropCommand());
//        addCommand(new UseCommand());
//        addCommand(new ShowCommand());
//        addCommand(new SizeCommand());
//        addCommand(new CommitCommand());
//        addCommand(new RollbackCommand());
//        addCommand(new ListCommand());
    }
    
    public void addCommand(DatabaseCommand command) {
        commander.addCommand(command);
    }
    
    public void interactiveMode(InterpreterStateDatabase state) {
        BufferedReader inReader = new BufferedReader(in);
        while (true) {
            String input;
            try {
                while (!inReader.ready()) {
                    if (Thread.currentThread().isInterrupted()) {
                        inReader.close();
                        return;
                    }
                }
                input = inReader.readLine();
            } catch (IOException e) {
                return;
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
    
    public void batchMode(InterpreterStateDatabase state, String arg) throws Exception {
        String[] commands = arg.split(";");
        for (String command : commands) {
            commander.startCommand(state, command);
        }
    }

    public Set<DatabaseCommand> getCommands() {
        return commander.getCommands();
    }
}

