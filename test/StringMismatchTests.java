import com.eaio.stringsearch.MismatchSearch;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/12/11
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringMismatchTests implements StringSearchTests {

    private MismatchSearch mismatchSearch;

    public StringMismatchTests(MismatchSearch mismatchSearch) {
        this.mismatchSearch = mismatchSearch;
    }

    public void testMismatchAtOnePosition() throws AssertionFailureException {
        /**
         * Test 1
         */
        String target = "thequickbrownfox";
        String pattern = "quack";
        int numOfMismatches = 1;

        System.out.print("Running mismatch test with target: " + target + ", pattern: " + pattern + ", k: " + numOfMismatches);
        int[] results = mismatchSearch.searchString(target, pattern, numOfMismatches);
        System.out.print(", Result: " + results[0] + ", " + results[1]);
        Assert.assertEquals(3, results[0]);
        Assert.assertEquals(1, results[1]);
    }

    public void testMismatchWithSingleCharacterString() throws AssertionFailureException {
        /**
         * Test 2
         */
        String target = "t";
        String pattern = "q";
        int numOfMismatches = 1;

        System.out.print("Running mismatch test with target: " + target + ", pattern: " + pattern + ", k: " + numOfMismatches);
        int[] results = mismatchSearch.searchString(target, pattern, numOfMismatches);
        System.out.print(", Result: " + results[0] + ", " + results[1]);
        Assert.assertEquals(0, results[0]);
        Assert.assertEquals(1, results[1]);
    }

    public void testMismatchWithRange() throws AssertionFailureException {
        /**
         * Test 3
         */
        String target = "thequickquickbrownfox";
        String pattern = "abde";
        int startIndex = 4;
        int endIndex = target.length() - 1;
        int numOfMismatches = 4;

        System.out.print("Running mismatch test with target: " + target + ", pattern: " + pattern + ", k: " + numOfMismatches);
        int[] results = mismatchSearch.searchString(target, startIndex, endIndex, pattern, numOfMismatches);
        System.out.print(", Result: " + results[0] + ", " + results[1]);

        Assert.assertEquals(startIndex, results[0]);
        Assert.assertEquals(numOfMismatches, results[1]);
    }
}
