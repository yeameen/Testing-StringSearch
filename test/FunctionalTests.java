import com.eaio.stringsearch.*;

import java.lang.reflect.Method;

import static java.lang.System.out;

/**
 * This is the entry point for Part 1: Functional tests
 */
public class FunctionalTests {

    private static int numTestCases = 0;
    private static int passedTestCases = 0;
    private static int crashedTestCases = 0;

    private static StringSearch instanceBNDM = new BNDM();
    private static StringSearch instanceBNDMCI = new BNDMCI();
    private static StringSearch instanceBNDMWildcards = new BNDMWildcards();
    private static StringSearch instanceBNDMWildcardsCI = new BNDMWildcardsCI();
    private static StringSearch instanceBoyerMooreHorspool = new BoyerMooreHorspool();
    private static StringSearch instanceBoyerMooreHorspoolRaita = new BoyerMooreHorspoolRaita();
    private static MismatchSearch instanceMismatchSearch = new ShiftOrMismatches();


    /**
     * Run all test methods of the instance of StringSearchTests. The method expects all the test cases start with
     * prefix "test" and uses reflection to iterate. In case of {@link AssertionFailureException}, it catches and
     * tries to make a readable output of the failure reason.
     *
     * @param test An instance of StringSearchTests with test cases
     */
    private static void runTestMethods(StringSearchTests test) {
        Class testsClass = test.getClass();
        for (Method declaredMethod : testsClass.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test") && !declaredMethod.isAnnotationPresent(SkipTest.class)) {
                out.println("Testing - " + TestHelper.splitCamelCase(declaredMethod.getName().substring(4)));

                numTestCases++;
                try {
                    declaredMethod.invoke(test);

                    passedTestCases++;

                    out.println("  (PASSED)");

                } catch (IllegalAccessException e) {
                    out.println("Wrong argument passed to - " + declaredMethod.getName());
                    e.printStackTrace(out);

                } catch(Exception e) {
                    if (e.getCause() instanceof AssertionFailureException) {

                        // Shows a formatted output of the failure reason
                        out.println();
                        out.println("  *******************************************************************************************************");
                        out.println("  Failed! at " + e.getCause().getStackTrace()[1].toString());
                        out.println("      Caller: at " + e.getCause().getStackTrace()[2].toString());
                        out.println("      Caller: at " + e.getCause().getStackTrace()[3].toString());
                        out.println("  " + e.getCause().getMessage());
                        out.println("  *******************************************************************************************************");
                    } else {

                        // Its not an assertion failure. The library crashes!
                        crashedTestCases++;
                        out.println();
                        out.println("The library crashed while calling '" + declaredMethod.getName() + "' for reason - " + e.getMessage());
                        e.printStackTrace(out);
                    }

                }
            }
        }
    }

    /**
     * Run string matching test cases on all the {@link StringSearch} instances.
     *
     * At first configure TestCase class according to what option is supported by the instance. Then run the tests
     *
     * @param stringSearchInstances The array of StringSearch instances which should be tested
     * @param isIgnoreCase          Is the instance supports ignore case matching
     * @param isWildcards           Is the instance supports wildcars
     */
    private static void runStringMatchTests(StringSearch[] stringSearchInstances, boolean isIgnoreCase, boolean isWildcards) {

        for(StringSearch stringSearchInstance : stringSearchInstances) {

            System.out.println("\nUsing implementation: " + stringSearchInstance.getClass().getName());

            StringSearchTests testCases = new StringMatchTests(stringSearchInstance, isIgnoreCase, isWildcards);
            runTestMethods(testCases);

            if(isIgnoreCase) {
                testCases = new IgnoreCaseTests(stringSearchInstance);
                runTestMethods(testCases);
            }

            if(isWildcards) {
                testCases = new WildcardsTests(stringSearchInstance);
                runTestMethods(testCases);
            }
        }
    }

    public static void main(String[] args) {

        // false, false; may not support ignore case and wildcards
        StringSearch[] stringSearchInstances = {instanceBNDM, instanceBNDMCI, instanceBNDMWildcards, instanceBNDMWildcardsCI, instanceBoyerMooreHorspool, instanceBoyerMooreHorspoolRaita};

        // false, true; supports wildcards but may not support ignore case
        StringSearch[] stringSearchWildcardInstances = {instanceBNDMWildcards, instanceBNDMWildcardsCI};

        // true, false; supports ignore case, but may not support wildcards
        StringSearch[] stringSearchIgnoreCaseInstances = {instanceBNDMCI, instanceBNDMWildcardsCI};

        // true, true; supports both wildcards and ignore case
        StringSearch[] stringSearchWildcardsIgnoreCaseInstances = {instanceBNDMWildcardsCI};

        out.println("\n\nTesting stringsearch with straight-forward string matching");
        runStringMatchTests(stringSearchInstances, false, false);

        out.println("\n\nTesting stringsearch with wildcards matching");
        runStringMatchTests(stringSearchWildcardInstances, false, false);

        out.println("\n\nTesting stringsearch with ignore case");
        runStringMatchTests(stringSearchIgnoreCaseInstances, true, false);

        out.println("\n\nTesting stringsearch wildcards + ignore case");
        runStringMatchTests(stringSearchWildcardsIgnoreCaseInstances, true, true);

        // Show the summary
        out.println();
        out.println("Total number of test cases: " + numTestCases);
        out.println("Passed: " + passedTestCases + ", Failed: " +  (numTestCases-passedTestCases-crashedTestCases
                + ", Crashed: " + crashedTestCases));
    }
}
