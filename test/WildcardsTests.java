import com.eaio.stringsearch.StringSearch;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/11/11
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class WildcardsTests implements StringSearchTests {
    private StringSearch stringSearch;

    public WildcardsTests(StringSearch stringSearch) {
        this.stringSearch = stringSearch;
    }

    public void testAllWildcards() throws AssertionFailureException {
        /**
         * Test 16
         *
         * Technique used: Boundary value input
         *
         * This test checks boundary value of inputs to any implementation of StringSearch
         * that supports wildcards. This particular test case pass all wildcards as the pattern input
         *
         * Input:
         * - a non null non empty string as target
         * - an all wildcards string as pattern
         *
         * Expected behaviour:
         * - Should return 0 as the match index
         */

        String target = "thequickbrown";
        int patternLength = (int)(Math.random() * target.length());

        if (patternLength == 0) {
            patternLength = 1;
        }

        char[] patternArray = new char[patternLength];
        Arrays.fill(patternArray, '.');

        String pattern = String.valueOf(patternArray);

        TestHelper.match(stringSearch, target, pattern, 0);
    }
}
