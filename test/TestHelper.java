import com.eaio.stringsearch.StringSearch;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/10/11
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestHelper {
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
