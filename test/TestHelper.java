import com.eaio.stringsearch.StringSearch;

/**
 * This class contains common utility methods used by multiple functional tests classes
 */
public class TestHelper {
    /**
     * @param s camel case name
     * @return human readable string of the camelCase name
     */
    public static String splitCamelCase(String s) {
       return s.replaceAll(
          String.format("%s|%s|%s",
             "(?<=[A-Z])(?=[A-Z][a-z])",
             "(?<=[^A-Z])(?=[A-Z])",
             "(?<=[A-Za-z])(?=[^A-Za-z])"
          ),
          " "
       );
    }

    static void match(StringSearch stringSearch, String target, String pattern, int expectedPosition) throws AssertionFailureException {
        System.out.print("  target: " + target + ", pattern: " + pattern + ", expected: " + expectedPosition);
        int location = stringSearch.searchString(target, pattern);

        Assert.assertEquals(expectedPosition, location);
    }

    static void match(StringSearch stringSearch, String target, String pattern, int expectedPosition, int startIndex) throws AssertionFailureException {
        System.out.print("  target: " + target + ", pattern: " + pattern + ", start: " + startIndex + ", expected: " + expectedPosition);
        int location = stringSearch.searchString(target, startIndex, pattern);

        Assert.assertEquals(expectedPosition, location);
    }

    static void match(StringSearch stringSearch, String target, String pattern, int expectedPosition, int startIndex, int endIndex) throws AssertionFailureException {
        System.out.print("  target: " + target + ", pattern: " + pattern + ", start: " + startIndex + ", end: " + endIndex + ", expected: " + expectedPosition);
        int location = stringSearch.searchString(target, startIndex, endIndex, pattern);

        Assert.assertEquals(expectedPosition, location);
    }


}
