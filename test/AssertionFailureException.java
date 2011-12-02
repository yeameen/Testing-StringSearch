/**
 * Custom Exception that is thrown in case of any mismatch between actual and expected behaviour
 */
public class AssertionFailureException extends Exception {

    public AssertionFailureException() {
        super();
    }

    public AssertionFailureException(String message) {
        super(message);
    }

    public AssertionFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertionFailureException(Throwable cause) {
        super(cause);
    }
}
