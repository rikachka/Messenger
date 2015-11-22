package project.client.commands;

import project.client.Session;
import project.client.exceptions.NotCriticalException;
import project.common.messages.MessageType;
import project.common.messages.Message;

import java.io.IOException;

public class SendCommand implements Command {
    private final String SEND_COMMAND_PREFIX ="/";

    public void execute(Session session, String[] args) throws Exception {
        if (session.getSocket().isInputShutdown()) {
            throw new Exception("Server disconnected");
        }
        Message message;
        try {
            String commandName = args[0].substring(SEND_COMMAND_PREFIX.length());
            MessageType messageType = MessageType.valueOf(commandName.toUpperCase());
            String commandArgs = "";
            if (args.length > 1) {
                commandArgs = args[1];
            }
            message = new Message(messageType, commandArgs);
        } catch (IllegalArgumentException e) {
            throw new NotCriticalException("No such command");
        }
        session.socketStream.write(message);

//        try {
//            Thread.sleep(1000);
//
//            // TODO: судя по всему клиент не асинхронный. В примере чтение из сокета было в отдельном потоке
//            while (session.socketStream.available() > 0) {
//                System.out.println("reading");
//                Message answer = session.socketStream.read();
//                if (!answer.getContent().isEmpty()) {
//                    if (answer.getType() == MessageType.ERROR) {
//                        session.err.println("answer: " + answer.getContent());
//                        session.err.flush();
//                    } else {
//                        session.out.println("answer: " + answer.getContent());
//                        session.out.flush();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new Exception("Server disconnected");
//        }

//        String commandToServer = Utils.concatStrings(args, " ");
//        session.toServerStream.println(commandToServer);
//        try {
//            Thread.sleep(100);
//
//            // TODO: судя по всему клиент не асинхронный. В примере чтение из сокета было в отдельном потоке
//            do {
//                String answerFromServer = session.fromServerStream.readLine();
//                session.out.println(answerFromServer);
//            } while (session.fromServerStream.ready());
//        } catch (IOException e) {
//            throw new Exception("Server disconnected");
//        }
    }
    
    public String name() {
        return "send";
    }
    
    public int minArgs() { //name of the command is not given as arg[0]
        return 1;
    }
    
    public int maxArgs() {
        return 2;
    }
}
