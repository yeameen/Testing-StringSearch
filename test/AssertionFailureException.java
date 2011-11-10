/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/10/11
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
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
