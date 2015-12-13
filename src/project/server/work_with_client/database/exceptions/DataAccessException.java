package project.server.work_with_client.database.exceptions;


public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Exception ex) {
        super(ex);
    }
}
