import com.eaio.stringsearch.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

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


    private static String splitCamelCase(String s) {
       return s.replaceAll(
          String.format("%s|%s|%s",
             "(?<=[A-Z])(?=[A-Z][a-z])",
             "(?<=[^A-Z])(?=[A-Z])",
             "(?<=[A-Za-z])(?=[^A-Za-z])"
          ),
          " "
       );
    }

    private static void runStringTests(StringSearch[] stringSearchInstances, boolean isIgnoreCase, boolean isWildcards) {

        for(StringSearch stringSearchInstance : stringSearchInstances) {

            System.out.println("\nRunning class: " + stringSearchInstance.getClass().getName());

            final StringContainTests test = new StringContainTests(stringSearchInstance, isIgnoreCase, isWildcards);
            final Class testsClass = StringContainTests.class;

            for (Method declaredMethod : testsClass.getDeclaredMethods()) {
                if (declaredMethod.getName().startsWith("test")) {
                    out.println("Running test - " + splitCamelCase(declaredMethod.getName().substring(4)));

                    numTestCases++;
                    try {
                        declaredMethod.invoke(test);

                        passedTestCases++;

                    } catch (IllegalAccessException e) {
                        out.println("Wrong argument passed to - " + declaredMethod.getName());
                        e.printStackTrace(out);

                    } catch (InvocationTargetException e) {
                        out.println("Something went wrong while trying to invoke the test - " + declaredMethod.getName());
                        e.printStackTrace(out);

                    } catch(Exception e) {
                        out.println("The library crashed while calling '" + declaredMethod.getName() + "' for reason - " + e.getMessage());
                        e.printStackTrace(out);

                    }
                }
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

        out.println("Running all");
        runStringTests(stringSearchInstances, false, false);

        out.println("\n\nRunning wildcards matching");
        runStringTests(stringSearchWildcardInstances, false, false);

        out.println("\n\nRunning ignore case matching");
        runStringTests(stringSearchIgnoreCaseInstances, true, false);

        out.println("\n\nRunning wildcards + ignore case matching");
        runStringTests(stringSearchWildcardsIgnoreCaseInstances, true, true);


        out.println();
        out.println("Total number of test cases: " + numTestCases);
        out.println("Passed: " + passedTestCases + ", Failed: " +  (numTestCases-passedTestCases));
    }
}
