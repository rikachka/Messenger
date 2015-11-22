package project.client.exceptions;

/**
 * Created by rikachka on 20.11.15.
 */
public class NotCriticalException extends Exception {

    public NotCriticalException() {}

    public NotCriticalException(String s) {
        super(s);
    }

    public NotCriticalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCriticalException(Throwable cause) {
        super(cause);
    }
}
