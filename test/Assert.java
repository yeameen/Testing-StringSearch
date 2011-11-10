/**
 * Helper "assert" class for the SE 6367 project.
 *
 * Feel free to add to this class.
 */
public class Assert {

    /**
     * Throws an exception if 'value' is false.  Includes a failure message for debugging.
     *
     * @param value          if false, an exception is thrown
     * @param failureMessage a reason or hint to aid in debugging
     */
    public static void assertIsTrue(boolean value, String failureMessage) throws AssertionFailureException {
        if (!value) {
            throw new AssertionFailureException(failureMessage);
        }
    }

    /**
     * Throws an exception if 'expected' is not equal to 'actual'
     *
     * @param expected the expected value
     * @param actual   the actual value
     */
    public static void assertEquals(int expected, int actual) throws AssertionFailureException {
        if (expected != actual) {
            throw new AssertionFailureException(
                    String.format("Assertion failure: expected=%d, actual=%d.", expected, actual)
            );
        }
    }
}
