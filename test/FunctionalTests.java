import com.eaio.stringsearch.*;

import java.lang.reflect.Method;

import static java.lang.System.out;

/**
 * This is the entry point for Part 1: Functional tests
 */
public class FunctionalTests {

    private static int numTestCases = 0;
    private static int passedTestCases = 0;

    private static StringSearch instanceBNDM = new BNDM();
    private static StringSearch instanceBNDMCI = new BNDMCI();
    private static StringSearch instanceBNDMWildcards = new BNDMWildcards();
    private static StringSearch instanceBNDMWildcardsCI = new BNDMWildcardsCI();
    private static StringSearch instanceBoyerMooreHorspool = new BoyerMooreHorspool();
    private static StringSearch instanceBoyerMooreHorspoolRaita = new BoyerMooreHorspoolRaita();
    private static MismatchSearch instanceMismatchSearch = new ShiftOrMismatches();


    private static void runTestMethods(StringSearchTests test) {
        Class testsClass = test.getClass();
        for (Method declaredMethod : testsClass.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test")) {
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
                        out.println();
                        out.println("  *******************************************************************************************************");
                        out.println("  Failed! at " + e.getCause().getStackTrace()[1].toString());
                        out.println("      Caller: at " + e.getCause().getStackTrace()[2].toString());
                        out.println("  " + e.getCause().getMessage());
                        out.println("  *******************************************************************************************************");
                    } else {
                        out.println();
                        out.println("The library crashed while calling '" + declaredMethod.getName() + "' for reason - " + e.getMessage());
                        e.printStackTrace(out);
                    }

                }
            }
        }
    }

    private static void runStringMismatchTests(MismatchSearch mismatchSearch) {
        StringSearchTests testCases = new StringMismatchTests(mismatchSearch);
        runTestMethods(testCases);
    }

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

        // false, false
        StringSearch[] stringSearchInstances = {instanceBNDM, instanceBNDMCI, instanceBNDMWildcards, instanceBNDMWildcardsCI, instanceBoyerMooreHorspool, instanceBoyerMooreHorspoolRaita};

        // false, true
        StringSearch[] stringSearchWildcardInstances = {instanceBNDMWildcards, instanceBNDMWildcardsCI};

        // true, false
        StringSearch[] stringSearchIgnoreCaseInstances = {instanceBNDMCI, instanceBNDMWildcardsCI};

        // true, true
        StringSearch[] stringSearchWildcardsIgnoreCaseInstances = {instanceBNDMWildcardsCI};

        out.println("\n\nTesting stringsearch with straight-forward string matching");
        runStringMatchTests(stringSearchInstances, false, false);

        out.println("\n\nTesting stringsearch with wildcards matching");
        runStringMatchTests(stringSearchWildcardInstances, false, false);

        out.println("\n\nTesting stringsearch with ignore case");
        runStringMatchTests(stringSearchIgnoreCaseInstances, true, false);

        out.println("\n\nTesting stringsearch wildcards + ignore case");
        runStringMatchTests(stringSearchWildcardsIgnoreCaseInstances, true, true);

        out.println("\n\nTesting stringsearch with mismatch");
        runStringMismatchTests(instanceMismatchSearch);

        out.println();
        out.println("Total number of test cases: " + numTestCases);
        out.println("Passed: " + passedTestCases + ", Failed: " +  (numTestCases-passedTestCases));
    }
}
