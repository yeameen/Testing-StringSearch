import com.eaio.stringsearch.BoyerMooreHorspool;
import com.eaio.stringsearch.StringSearch;

/**
 * @author Chowdhury Ashabul Yeameen
 * @since 11/1/11
 */
public class StringMatchTests implements StringSearchTests {

    private StringSearch stringSearch;

    private boolean isIgnoreCase;
    private boolean isWildcards;

    public StringMatchTests(StringSearch stringSearch, boolean isIgnoreCase, boolean isWildcards) {
        this.stringSearch = stringSearch;
        this.isIgnoreCase = isIgnoreCase;
        this.isWildcards = isWildcards;
    }

    private int getRandomIntWithinRange(int range) {
        return (int)(Math.random() * range);
    }

    private String getSampleTarget(String input) {
        String output = input;
            int length = input.length();

            if(isIgnoreCase) {

                // Step 1: Random number of characters to replace
                int numberOfCharactersToReplace = getRandomIntWithinRange(length);

                char[] chars = input.toCharArray();
                for(int i = 0; i < numberOfCharactersToReplace; i++) {

                    // Step 2: Pick a random position
                    int randomPosition = getRandomIntWithinRange(length);

                    // Step 3: Replace
                    if(Character.isUpperCase(chars[randomPosition])) {
                        chars[randomPosition] = Character.toLowerCase(chars[randomPosition]);
                    } else {
                        chars[randomPosition] = Character.toUpperCase(chars[randomPosition]);
                    }
                }
                output = new String(chars);
            }
        return output;

    }

    private String getSamplePattern(String input) {

        // Ignorecase part is same for target & pattern
        String output = getSampleTarget(input);
        int length = input.length();

        if(isWildcards) {
            int numberOfCharactersToReplace = getRandomIntWithinRange(length);

            if (isWildcards && numberOfCharactersToReplace > length - 2) {
                // Its not a good idea to replace all the characters by wildcards. So reduce the number
                numberOfCharactersToReplace = length - 2;
            }

            char[] chars = input.toCharArray();
            for(int i = 0; i < numberOfCharactersToReplace; i++) {
                int randomPosition = getRandomIntWithinRange(length);
                chars[randomPosition] = '.';
            }
            output = new String(chars);
        }

        return output;
    }



    void match(String target, String pattern, int expectedPosition) throws AssertionFailureException {
        TestHelper.match(stringSearch, target, pattern, expectedPosition);
    }

    void match(String target, String pattern, int expectedPosition, int startIndex) throws AssertionFailureException {
        TestHelper.match(stringSearch, target, pattern, expectedPosition, startIndex);
    }

    void match(String target, String pattern, int expectedPosition, int startIndex, int endIndex) throws AssertionFailureException {
        TestHelper.match(stringSearch, target, pattern, expectedPosition, startIndex, endIndex);
    }



    /*******************************************************************************************************************
     * PART 1: String match without range
     *
     * parameters:
     * - a target string
     * - a pattern
     *
     ******************************************************************************************************************/
    void testMismatch() throws AssertionFailureException {
        /**
         * Technique used: Equivalence Input Partitioning
         *
         * This test case represents the equivalence class of test inputs - the pattern doesn't match
         * with the target string
         *
         *
         *  - a non-null pattern
         *  - a non-null string
         *  - the pattern is NOT found in the string.
         *
         */

        String target = getSampleTarget("thequickbrownfox");
        String pattern = getSamplePattern("jumpover");

        match(target, pattern, -1);

    }

    void testMatchingSomewhere() throws AssertionFailureException {
        /**
         * Test 1
         *
         * Technique used: Equivalence Input Partitioning
         *
         * This test case considers boundary value of the output to any implementation of StringSearch.
         * This particular test case passes inputs that pattern matches somewhere at the middle of the target string
         *
         * Input:
         * - a target string
         * - a pattern that match somewhere at the middle of the target
         * Expected behaviour:
         * - the pattern is found at the middle of target where it matches
         */

        String target = getSampleTarget("thequickbrownfox");
        String pattern = getSamplePattern("quick");

        match(target, pattern, 3);

    }

    void testNullInput() throws AssertionFailureException {

        /**
         * Test 2
         * Technique used: Equivalence Input Partitioning.
         *
         * This test case represents equivalence class of test inputs to any implementation of StringSearch.
         * Since the documentation says parameters may not be null - the program is expected to throw Exception, though
         * may not be a good practice
         *
         * Any illegal input null actually results in raising Exception in this library, though I don't think
         * this is the right way to do this. At least the library can wrap this in its own exception
         *
         * Input:
         * - a null target
         * - a null pattern
         * Expected Behavior:
         * - should throw NullPointerException
         *
         * Input:
         * - a null target
         * - a non-null pattern
         * Expected Behavior:
         * - should throw NullPointerException
         *
         * Input:
         * - a non-null target
         * - a null pattern
         * Expected Behavior:
         * - should throw NullPointerException
         */

        int numOfExceptions = 0;

        // When both of the inputs are null
        String target = null;
        String pattern = null;

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        // When only the pattern is null
        pattern = "quick";

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        // When only the target string is null
        target = "thequickbrown";
        pattern = null;

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        // All should through exception
        Assert.assertIsTrue(3 == numOfExceptions, "Illegal input(s) throw exception(s)");
    }

    // Note: throws exception on StringSearch v2.
    void testEmptyTargetAndPattern() throws  AssertionFailureException {
        /**
         * Test 3
         * Technique used: Boundary Value Analysis.
         *
         * This test case represents some boundary values of inputs to any implementation of StringSearch.
         * Empty inputs are legal according to API but should not show a positive match.
         *
         * Input:
         * - a non-null empty target
         * - a non-null empty pattern
         * Expected Behavior:
         * The pattern doesn't match with the target string
         */

        String target = "";
        String pattern = "";

        match(target, pattern, -1);
    }

    // Note: throws exception on StringSearch v2.
    void testEmptyTarget() throws AssertionFailureException {
        /**
         * Test 4
         * Technique used: Boundary Value Analysis.
         *
         * This test case represents some boundary values of inputs to any implementation of StringSearch.
         *
         *
         * Input:
         * - a non-null non-empty pattern
         * - a non-null empty string
         * - the pattern is NOT found in the string
         *
         * Expected Behavior:
         * The pattern doesn't match with the target string
         */

        String target = "";
        String pattern = getSamplePattern("abcde");

        match(target, pattern, -1);
    }

    // Note: throws exception on StringSearch v2. Tested both parameters with empty strings individually.
    // Most likely no checking of input and interference of the bugs
    void testEmptyPattern() throws  AssertionFailureException {
        /**
         * Test 5
         * Technique used: Boundary Value Analysis.
         *
         * This test case represents some boundary values of inputs to any implementation of StringSearch - empty inputs.
         * Empty inputs are legal according to API but should not show a positive match.
         *
         * Input:
         * - a non-null empty pattern
         * - a non-null string
         *
         * Expected Behavior:
         * The pattern doesn't match with the target string
         */

        String target = getSampleTarget("something");
        String pattern = "";

        match(target, pattern, -1);
    }

    void testMatchingAtBeginning() throws AssertionFailureException {
        /**
         * Test 6
         *
         * Technique used: Boundary Value Analysis
         *
         * This test case considers boundary value of the output to any implementation of StringSearch. Checks match
         * at the starting of the given string.
         *
         * Input:
         * - a target string
         * - a pattern that should match at the beginning of the string
         * Expected behaviour:
         * - the pattern is found at position 0
         */

        String target = getSampleTarget("thequickbrown");
        String pattern = getSamplePattern("thequick");

        match(target, pattern, 0);
    }


    void testMatchingAtEnd() throws AssertionFailureException {

        /**
         * Test 7
         *
         * Technique used: Boundary Value Analysis
         *
         * This test case considers boundary value of the output to any implementation of StringSearch. Checks match
         * at the that occurs at the end of the given string.
         *
         * Input:
         * - a target string
         * - a pattern that matches at the end of the string
         * Expected behaviour:
         * - the pattern is at difference between the length of two string
         */

        String target = getSampleTarget("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ac ligula sed lorem posuere tristique. Donec commodo accumsan mauris. Nunc in est ac ligula iaculis pretium. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam cursus pharetra eros, at consectetur lacus laoreet id. Vestibulum consectetur scelerisque laoreet. Nam ac egestas nulla. Fusce ac nunc libero, vel pretium magna. Vestibulum vehicula hendrerit urna quis sagittis.\n" +
                "\n" +
                "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla arcu lorem, pharetra at malesuada ac, molestie eget erat. Proin at nisl urna. Etiam posuere, magna ac eleifend vestibulum, nibh quam vulputate justo, quis faucibus leo massa ac mauris. Phasellus bibendum, nibh in iaculis hendrerit, nibh dolor tristique nisl, vel dapibus diam massa id tellus. Donec iaculis sapien eget mauris semper mollis interdum eros iaculis. Proin at lacus tellus, eget molestie erat. Ut tincidunt erat et dolor fringilla posuere. Aliquam dignissim lacinia nisi, non fringilla quam tempor nec. Suspendisse ac est in turpis gravida auctor. Ut tempor sem in dui laoreet sed pretium mauris ornare. Suspendisse quis nisi elit.\n" +
                "\n" +
                "Sed diam elit, pretium eget dictum et, convallis tempus augue. Nunc nec rutrum lorem. Aliquam placerat placerat tellus, nec facilisis sapien hendrerit vel. Etiam viverra gravida orci, vitae condimentum nulla lacinia nec. Pellentesque ultrices dignissim tellus, id sagittis massa molestie ac. Morbi ac libero lectus. Vestibulum hendrerit orci vitae dui scelerisque bibendum. Praesent sagittis luctus vestibulum. In ultrices pellentesque nisi vitae euismod. Ut semper porttitor turpis, id pharetra leo commodo eget. Aliquam pellentesque ultricies mollis. Ut gravida turpis nec arcu rhoncus gravida. Nullam id nibh congue elit luctus porttitor vel eu risus. Proin fringilla faucibus arcu, eu semper dui laoreet sodales. Mauris ultricies faucibus sapien quis ultrices.\n" +
                "\n" +
                "Morbi mollis hendrerit ultrices. Pellentesque non eleifend orci. Sed congue felis eu leo fringilla vestibulum. Nullam consectetur, eros mattis ultrices tempor, mauris tellus aliquet enim, vitae vestibulum nibh lectus a libero. Nulla pellentesque consectetur vestibulum. Sed sed leo ipsum, ut rutrum purus. Maecenas in consectetur est. Maecenas sit amet ante ut augue sodales scelerisque. Maecenas semper, turpis vitae eleifend egestas, odio nulla tristique dolor, sit amet tempus lectus elit blandit urna. Vivamus varius consequat diam non placerat. Quisque vulputate eleifend laoreet. Donec gravida arcu ac dui dictum aliquam. Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "\n" +
                "Ut a nulla quis leo condimentum pretium eget ac sem. Sed orci sem, ultricies ac vestibulum nec, consequat quis nisi. Nam accumsan mauris et elit ultrices rutrum. Etiam quis mauris eu turpis ultricies ultrices. Nunc eu arcu hendrerit enim sagittis tristique sed a dolor. Duis sem libero, pulvinar vel condimentum vitae, ultrices vulputate orci. Vestibulum laoreet egestas neque vitae viverra. In hac habitasse platea dictumst.$");
        String pattern = "$";

        match(target, pattern, target.length()-1);
    }

    void testMatchingSingleCharacterString() throws AssertionFailureException {
        /**
         * Test 8
         *
         * Technique used: Boundary Value Analysis
         *
         * This test case considers boundary values of inputs to any implementation of StringSearch. Checks match
         * between exactly two single character string
         *
         * Input:
         * - a single character target string
         * - a single character pattern with the same character as the target string
         * Expected behaviour:
         * - the pattern is found at position 0
         */

        String target = getSampleTarget("a");
        String pattern = getSamplePattern("a");

        match(target, pattern, 0);

    }

    void testFullMatch() throws AssertionFailureException {
        /**
         * Test 9
         *
         * Technique used: Boundary Value Analysis
         *
         * This test case checks string matching between two exact same length string.
         *
         * Input:
         * - a target string
         * - a pattern that is same as the input string (may have wildcard or case variant)
         * Expected Behavior:
         * - the pattern is found at position 0
         *
         */

        String target = getSampleTarget("thequickbrownfox");
        String pattern = getSamplePattern("thequickbrownfox");

        match(target, pattern, 0);
    }

    void testLargerPatternWithPartialMatch() throws AssertionFailureException {
        /**
         * Test 10
         *
         * Technique used: Boundary Value Analysis
         *
         * This test case tries to match between two strings where target is a substring of the pattern.
         *
         * Input:
         * - a target string
         * - a pattern that is one character longer than the target and contains the target in the beginning
         *
         * Expected Behavior
         * - the pattern not found in the target
         *
         */

        String target = getSampleTarget("thequickbrownfox");
        String pattern = getSamplePattern("thequickbrownfoxj");

        match(target, pattern, -1);
    }

    void testSpecialCharacterMatch() throws AssertionFailureException {
        /**
         * Test 11
         *
         * Technique used: Equivalence Input Partitioning & Boundary Value.
         * - Equivalence in the sense this particular test uses special characters.
         * - Boundary in the sense all the characters in the pattern are special characters; testing whether
         *     library can handle all these
         *
         * This test case runs matching between strings with special characters
         *
         * Input:
         * - a target string with special character
         * - a pattern of special characters that matches the target string
         *
         * Expected Behavior
         * - the pattern found at the target
         */

        String target = "hello_+&^* ()-=!@#world";
        String pattern = "&^* ()";

        match(target, pattern, target.indexOf('&'));
    }

    void testMaximumAllowedPattern() throws AssertionFailureException {
        /**
         * Test 12
         *
         * Technique used: boundary value of input.
         *
         * Input:
         *  - a pattern of exactly 32 characters
         *  - a target string that contains the pattern
         * Expected behaviour:
         *  - the index of where the matching starts
         */

        // Other than BoyerMooreHorsepool implementations, all impose 32 character limit on pattern
        if (stringSearch instanceof BoyerMooreHorspool) {
            return;
        }

        String str = "Garbage Starting Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String pattern = getSamplePattern("Lorem ipsum dolor sit amet, cons");

        match(str, pattern, 17);

        pattern = getSamplePattern("Lorem ipsum dolor sit amet, consasdf9809238");

        // Still matches in all libraries except BoyerMooreHorsepool variants
        match(str, pattern, 17);
    }


    /*******************************************************************************************************************
     * PART 2: String match with starting range
     *
     * parameters:
     * - a target string
     * - a starting range
     * - a pattern
     *
     ******************************************************************************************************************/

    void testStartMatchingFromSomePosition() throws AssertionFailureException{
        /**
         * Test 13
         *
         * Technique used: Equivalence Input Partitioning
         *
         * This test case represents equivalence class of test inputs. The library starts searching for the pattern
         * in the target string from the given index. This particular test case passes index beyond the length of
         * the target string
         *
         * Input:
         * - a non-empty target string with the pattern twice
         * - a non-empty and a substring of target string as pattern
         * - a some index beyond te first match
         *
         * Expected Behavior:
         * - No match
         */
        String targetBeforeAltering = "thequickbrownquickbrown";
        String patternBeforeAltering = "quickb";
        String target = getSampleTarget(targetBeforeAltering);
        String pattern = getSamplePattern(patternBeforeAltering);

        // Looking for the the second match. So set starting point as one passed the first match
        int startIndex = targetBeforeAltering.indexOf(patternBeforeAltering) + 1;
        int expectedIndex = targetBeforeAltering.indexOf(patternBeforeAltering, startIndex);

        match(target, pattern, expectedIndex, startIndex);
    }

    void testNegativeStartIndex() throws AssertionFailureException {
        /**
         * Test 14
         *
         * Technique used: Equivalence Input Partitioning
         *
         * This test case represents equivalence class of test inputs. The library starts searching for the pattern
         * in the target string from the given index. This particular test case passes "negative index"
         * for start searching
         *
         * Input:
         * - A non-empty target string
         * - A non-empty and a substring of target string as pattern
         * - A negative start index
         *
         * Expected Behavior:
         * - The program should not crash
         * - Show exact index where the match begins
         */
        String target = getSampleTarget("thequickbrown");
        String pattern = getSamplePattern("quickb");
        int startIndex = -1;
        int expectedIndex = 3;

        match(target, pattern, expectedIndex, startIndex);
    }

    void testStartMatchingAtIndexBeyondTargetLength() throws AssertionFailureException {
        /**
         * Test 15
         *
         * Technique used: Equivalence Input Partitioning
         *
         * This test case represents equivalence class of test inputs. The library starts searching for the pattern
         * in the target string from the given index. This particular test case passes index beyond the length of
         * the target string
         *
         * Input:
         * - a non-empty target string
         * - a non-empty and a substring of target string as pattern
         * - a start index beyond the length of target string
         *
         * Expected Behavior:
         * - No match
         */

        String target = getSampleTarget("thequickbrown");
        String pattern = getSamplePattern("quickb");
        int startIndex = target.length();
        int expectedPosition = -1;

        match(target, pattern, expectedPosition, startIndex);
    }

    /*******************************************************************************************************************
     * PART 3: String match with starting and end range
     *
     * parameters:
     * - a target string
     * - a starting point (index) of target where to start searching
     * - an ending point (index) of target where to end searching
     * - a pattern
     *
     ******************************************************************************************************************/

    void testMatchingSomewhereInTheRange() throws AssertionFailureException{
        /**
         * Test 16
         *
         * Technique used: Equivalence Input Partitioning
         *
         * This test case represents equivalence class of test inputs. The method under testing searches for the string
         * for a given range. This test case search for the pattern in the range
         *
         * Input:
         * - a non-empty target string with the pattern twice
         * - a non-empty and a substring of target string as pattern
         * - an index beyond te first match
         * - an index beyond the match
         *
         * Expected Behavior:
         * - get the second match
         */
        String targetBeforeAltering = "thequickbrownquickbrown";
        String patternBeforeAltering = "quickb";
        String target = getSampleTarget(targetBeforeAltering);
        String pattern = getSamplePattern(patternBeforeAltering);

        // Looking for the the second match. So set starting point as one passed the first match
        int startIndex = targetBeforeAltering.indexOf(patternBeforeAltering) + 1;
        int endIndex = target.length() - 1;
        int expectedIndex = targetBeforeAltering.indexOf(patternBeforeAltering, startIndex);

        match(target, pattern, expectedIndex, startIndex, endIndex);
    }

    // Note: fails on StringSearch v2.
    void testSingleCharacterStringWithRange() throws AssertionFailureException {
        /**
         * Test 17
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test checks for the boundary values of the input parameters. This one checks if the method can finds
         * match between one character length strings
         *
         * Input:
         * - a single character length target string
         * - a single character lenght matching pattern
         * - start index as 0
         * - end index as 0
         *
         * Expected Behavior:
         * - Match at index 0
         */
        String target = getSampleTarget("t");
        String pattern = getSamplePattern("t");

        int startIndex = 0;
        int endIndex = 0;
        int expectedPosition = 0;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }

    // Note: also fails on StringSearch v2. May be the previous and this one caused by the same bug
    void testRangedSearchWithExactRangeOfMatch() throws AssertionFailureException {
        /**
         * Test 17
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test case checks for multiple character length string and same length pattern which is a match.
         * The starting and ending index are the starting and ending index of the target string.
         *
         * Input:
         * - a multiple character length target string
         * - a multiple character lenght matching pattern that should match with target
         * - start index as 0
         * - end index as the target length - 1
         *
         * Expected Behavior:
         * - Match at index 0
         */
        String target = getSampleTarget("abcd");
        String pattern = getSamplePattern("abcd");
        int startIndex = 0;
        int endIndex = target.length() - 1;
        int expectedPosition = 0;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }

    // Note: further tested to establish the failing point. Now tries to match at the end of the target string
    void testMatchingOccursAtEndIndex() throws AssertionFailureException {
        /**
         * Test 19
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test case checks single character match at the end of the target string. The range indices range starts
         * at the first of the target and ends at the end.
         *
         * Input:
         * - a multiple character length target string
         * - a single character length pattern that matches at the end of the target
         * - start index as 0
         * - end index as the target length - 1
         *
         * Expected Behavior:
         * - Match at the end of the target
         */
        String target = getSampleTarget("abcd");
        String pattern = getSamplePattern("d");
        int startIndex = 0;
        int endIndex = target.length() - 1;
        int expectedPosition = 3;

        match(target, pattern, expectedPosition, startIndex, endIndex);

    }

    // Note: Another test to figure out the minimal test input. Fails too! May be the problem is with the last index.
    void testMatchingSingleCharacterAtStartIndexAndEndIndex() throws AssertionFailureException {
        /**
         * Test 20
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test case checks single character match at the middle of the target string. This time the range ends
         * at the matching index
         *
         * Input:
         * - a multiple character length target string
         * - a single character length pattern which matches at somewhere in the middle of the target string
         * - start index as 0
         * - the index of the matching character in the target
         *
         * Expected Behavior:
         * - Match at the argument end index
         */

        String target = getSampleTarget("thequickbrown");
        String pattern = getSamplePattern("e");

        int startIndex = 2;
        int endIndex = 2;
        int expectedPosition = 2;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }


    // Another test. This time tries to match one character before last index. This one passed when it was written.
    // PASSED! Most likely the algorithm stops looking match one character before the last index.
    void testMatchingSingleCharacterAtOneCharacterBeforeEndIndex() throws AssertionFailureException {
        /**
         * Test 21
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test case checks single character match at the middle of the target string. This time the range ends
         * at one more than the matching index
         *
         * Input:
         * - a multiple character length target string
         * - a single character length pattern which matches at somewhere in the middle of the target string
         * - start index as 0
         * - end index as 1 more than the matching position
         *
         * Expected Behavior:
         * - Match at one less than the argument end index
         */

        String target = getSampleTarget("thequickbrown");
        String pattern = getSamplePattern("e");

        int startIndex = 2;
        int endIndex = 3;
        int expectedPosition = 2;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }

    void testMatchingOccursAtStartIndex() throws AssertionFailureException {
        /**
         * Test 22
         *
         * Technique used: Boundary Input Value Analysis
         *
         * This test case checks for string search with range where the match occurs the beginning. For checking
         * boundary value, the pattern is a single character length string.
         *
         * Input:
         * - a multiple character length target string
         * - a single character length pattern matching at the beginning of the target
         * - start index as 0
         * - end index as the target length - 1
         *
         * Expected Behavior:
         * - Match at index 0
         */
        String target = getSampleTarget("abcd");
        String pattern = getSamplePattern("a");
        int startIndex = 0;
        int endIndex = target.length();
        int expectedPosition = 0;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }

    // Note: fails if passed negative starting index in StringSearch. But the other variant, without end index,
    // doesn't crash for negative starting index.
    // Also @see StringMatchTests#testNegativeStartIndex() */
    void testRangedSearchWithNegativeStartIndex() throws AssertionFailureException {
        /**
         * Test 23
         *
         * Technique used: Equivalent Input Partitioning
         *
         * This test case checks for multiple character length string and a substring as the pattern.
         *
         * Input:
         * - a multiple character length target string
         * - a multiple character pattern that matches somewhere at the target
         * - start index as -1
         * - end index as the target length
         *
         * Expected Behavior:
         * - Match at index 0
         */
        String target = getSampleTarget("qwert");
        String pattern = getSamplePattern("we");

        int startIndex = -1;
        int endIndex = target.length();
        int expectedPosition = 1;

        match(target, pattern, expectedPosition, startIndex, endIndex);
    }
}
