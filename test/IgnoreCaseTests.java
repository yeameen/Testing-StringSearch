import com.eaio.stringsearch.StringSearch;
/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/11/11
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class IgnoreCaseTests implements StringSearchTests {
    private StringSearch stringSearch;

    private String target;
    private String pattern;
    private int expectedPosition;

    public IgnoreCaseTests(StringSearch stringSearch) {
        this.stringSearch = stringSearch;
        target = "thequickbrownfoxjumps";
        pattern = "quickbrownfox";
        expectedPosition = 3;

    }

    public void testAllSameCase() throws AssertionFailureException {
        /**
         * Test 14
         *
         * Technique used: boundary value.
         *
         * Input:
         *  - a target string of either all small or all uppercase
         *  - a substring of target as pattern with all characters in opposite case
         *
         * Output:
         *  - the index of where the matching starts
         */

        TestHelper.match(stringSearch, target.toLowerCase(), pattern.toLowerCase(), expectedPosition);

        TestHelper.match(stringSearch, target.toUpperCase(), pattern.toUpperCase(), expectedPosition);
    }

    public void testAllOppositeCase() throws AssertionFailureException {
        /**
         * Test 15
         *
         * Technique used: boundary value.
         *
         * Input:
         *  - a target string of either all small or all uppercase
         *  - a substring of target as pattern with all characters in same case
         *
         * Output:
         *  - the index of where the matching starts
         */

        TestHelper.match(stringSearch, target.toLowerCase(), pattern.toUpperCase(), expectedPosition);

        TestHelper.match(stringSearch, target.toUpperCase(), pattern.toLowerCase(), expectedPosition);
    }
}
