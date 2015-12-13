package project.server.work_with_client.database.exceptions;

public class IllegalDataStateException extends IllegalStateException {
    public IllegalDataStateException(String message) {
        super(message);
    }

    public IllegalDataStateException(Exception ex) {
        super(ex);
    }
}
