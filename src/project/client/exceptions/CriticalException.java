package project.client.exceptions;

/**
 * Created by rikachka on 20.11.15.
 */
public class CriticalException extends Exception {

    public CriticalException() {}

    public CriticalException(String s) {
        super(s);
    }

    public CriticalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CriticalException(Throwable cause) {
        super(cause);
    }
}
