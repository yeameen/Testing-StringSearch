import com.eaio.stringsearch.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.System.out;

/**
 * This is the entry point for Part 1: Functional tests
 */
public class FunctionalTests {

    public static void main(String[] args) {

        StringSearch stringSearch = new BNDM();
        StringSearch[] stringSearchInstances = {
                new BNDM(),
                new BNDMCI(),
                new BNDMWildcards(),
                new BNDMWildcardsCI(),
                new BoyerMooreHorspool(),
                new BoyerMooreHorspoolRaita()
        };

        int numTestCases = 0;
        int passedTestCases = 0;

        for(StringSearch stringSearchInstance : stringSearchInstances) {
            final StringContainTests test = new StringContainTests(stringSearchInstance);
            final Class bndmTestClass = StringContainTests.class;

            for (Method declaredMethod : bndmTestClass.getDeclaredMethods()) {
                if (declaredMethod.getName().startsWith("test")) {
                    out.println("Running test - " + declaredMethod.getName());

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


        // ... Create at least 20 more tests based on our requirements-based test
        // generation strategies.


        out.println();
        out.println("Total number of test cases: " + numTestCases);
        out.println("Passed: " + passedTestCases + ", Failed: " +  (numTestCases-passedTestCases));
    }
}
