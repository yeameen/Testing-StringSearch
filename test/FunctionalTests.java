import com.eaio.stringsearch.BNDM;
import com.eaio.stringsearch.StringSearch;

/**
 * This is the entry point for Part 1: Functional tests
 */
public class FunctionalTests {


    public static void main(String[] args) {

        // Test case 1:
        test1();

        // ... Create at least 20 more tests based on our requirements-based test
        // generation strategies.


        System.out.println("Tests completed successfully.");
    }

    static void test1() {
        // Technique used: Equivalence Partitioning.

        // This test case represents the equivalence class of test inputs
        // to the BNDM implementation of StringSearch.searchString that contain:

        //  - a non-null pattern
        //  - a non-null string
        //  - the pattern is NOT found in the string.

        StringSearch ss = new BNDM();
        String str = "helloworld";
        String pattern = "se6367";

        int location = ss.searchString(str, pattern);

        Assert.assertEquals(-1, location);

    }
}
