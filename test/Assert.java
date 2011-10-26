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
    public static void assertIsTrue(boolean value, String failureMessage) {
        if (!value) {
            throw new RuntimeException("Assertion failure: Message is \"" + failureMessage + "\"");
        }
    }

    /**
     * Throws an exception if 'expected' is not equal to 'actual'
     *
     * @param expected the expected value
     * @param actual   the actual value
     */
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException(
                    String.format("Assertion failure: expected=%d, actual=%d.", expected, actual)
            );
        }
    }
}
