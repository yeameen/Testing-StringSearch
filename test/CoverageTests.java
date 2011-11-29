import com.eaio.stringsearch.*;

import java.lang.reflect.Method;

import static java.lang.System.out;

/**
 * This is the entry point for Part 2, Coverage-enhanced tests.
 */
public class CoverageTests {

    private static int numTestCases = 0;

    public static void main(String[] args) {
        FunctionalTests.main(args);

        runCoverageTests();

        // Start adding additional tests here, below this line.

    }

    /**
     * Create and iterate over all StringSearch implementation and call test cases for each
     */
    private static void runCoverageTests() {

        // 1. Cover StringSearch without mismatch
        StringSearch[] stringSearchInstances = {
                new BNDM(),
                new BNDMCI(),
                new BNDMWildcards(),
                new BNDMWildcardsCI(),
                new BNDMWildcardsCI('?'),
                new BoyerMooreHorspool(),
                new BoyerMooreHorspoolRaita(),
                new ShiftOrMismatches()
        };

        out.println("\n\n\n Running Coverage Tests\n\n");

        for(StringSearch stringSearch : stringSearchInstances) {
            StringSearchTests testClass = new StringMatchCoverage(stringSearch);
            runTestMethods(testClass);
        }

        // 2. Cover mismatch search
        MismatchSearch mismatchSearch = new ShiftOrMismatches();
        runTestMethods(new StringMismatchCoverage(mismatchSearch));

        // 3. Cover CharIntMap
        runTestMethods(new CharIntMapCoverage());
    }

    /**
     * Run coverage test cases from the StringSearch instance. Using Jave reflection, it looks for all the test methods
     * that starts with prefix "test" and invoke. Since this method only cares for coverage, it will care for assertion
     * failure.
     *
     * @param test An initiated instance of {@link StringSearchTests}
     */
    private static void runTestMethods(StringSearchTests test) {
        Class testsClass = test.getClass();
        for (Method declaredMethod : testsClass.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test")) {
                out.println("Testing - " + TestHelper.splitCamelCase(declaredMethod.getName().substring(4)));

                numTestCases++;
                try {
                    declaredMethod.invoke(test);

                } catch (IllegalAccessException e) {
                    out.println("Wrong argument passed to - " + declaredMethod.getName());
                    e.printStackTrace(out);

                } catch(Exception e) {
                    out.println();
                    out.println("The library crashed while calling '" + declaredMethod.getName() + "' for reason - " + e.getMessage());
                    e.printStackTrace(out);
                }
            }
        }
    }
}
