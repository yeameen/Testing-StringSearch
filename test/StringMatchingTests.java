import com.eaio.stringsearch.StringSearch;

/**
 * @author Chowdhury Ashabul Yeameen
 * @since 11/1/11
 */
public class StringMatchingTests implements StringSearchTests {

    private StringSearch stringSearch;

    private boolean isIgnoreCase;
    private boolean isWildcards;

    public StringMatchingTests(StringSearch stringSearch, boolean isIgnoreCase, boolean isWildcards) {
        this.stringSearch = stringSearch;
        this.isIgnoreCase = isIgnoreCase;
        this.isWildcards = isWildcards;
    }

    private int getRandomIntWithinRange(int range) {
        return (int)(Math.random() * range);
    }

    private String getSamplePattern(String input) {
        String output = input;
        int length = input.length();

        if(isIgnoreCase) {
            int numberOfCharactersToReplace = getRandomIntWithinRange(length);

            char[] chars = input.toCharArray();
            for(int i = 0; i < numberOfCharactersToReplace; i++) {
                int randomPosition = getRandomIntWithinRange(length);
                if(Character.isUpperCase(chars[randomPosition])) {
                    chars[randomPosition] = Character.toLowerCase(chars[randomPosition]);
                } else {
                    chars[randomPosition] = Character.toUpperCase(chars[randomPosition]);
                }
            }
            output = new String(chars);
        }

        if(isWildcards) {
            int numberOfCharactersToReplace = getRandomIntWithinRange(length);

            if (isWildcards && numberOfCharactersToReplace > length - 2) {
                /* Its not a good idea to replace all the characters by wildcards. So reduce the number */
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

    void testMismatch() throws AssertionFailureException {
        // Technique used: Equivalence Input Partitioning.

        // This test case represents the equivalence class of test inputs
        // to any implementation of StringSearch.searchString that contain:

        //  - a non-null pattern
        //  - a non-null string
        //  - the pattern is NOT found in the string.

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("jumpover");

        match(str, pattern, -1);
    }


    void testIllegalInput() throws AssertionFailureException {

        /**
         * Test 1
         * Technique used: Equivalence Input Partitioning.
         *
         * This test case represents equivalence class of test inputs
         * to any implementation of StringSearch. Since the documentation says no parameter may be null - the program
         * is expected to through Exception
         *
         *
         * Any illegal input null should throw Exception. Though I don't think this is the right way to do this.
         * At least the library should wrap this in its own exception
         *
         * - a null target
         * - a null pattern
         * - should throw NullPointerException
         *
         * - a null target
         * - a non-null pattern
         * - should throw NullPointerException
         *
         * - a non-null target
         * - a null pattern
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
        Assert.assertIsTrue(3 == numOfExceptions, "Illegal input(s) should throw exception");
    }

    void testEmptyPattern() throws  AssertionFailureException {
        /**
         * Test 2
         * Technique used: Equivalence Input Partitioning.
         *
         * This test case represents equivalence class of test inputs
         * to any implementation of StringSearch.
         *
         * The pattern doesn't match with the target string
         *
         * - a non-null empty pattern
         * - a non-null string
         * - the pattern is NOT found in the string
         *
         */

        String str = "something";
        String pattern = "";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(-1, location);
    }

    void testStartMatchingWithNegativeIndex() throws AssertionFailureException {
        /**
         * Test 3
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
        String target = "thequickbrown";
        String pattern = getSamplePattern("quickb");
        int startIndex = -1;

        System.out.print("  target: " + target + ", pattern: " + pattern);

        int location = stringSearch.searchString(target, startIndex, pattern);
        Assert.assertEquals(3, location);

        System.out.println(" (PASSED)");
    }

    void testStartMatchingAtIndexBeyondTargetLength() throws AssertionFailureException {
        /**
         * Test 4
         *
         * Technique used: Equivalence Input Partitioning for the function:
         * public final int searchString(String text,
         *                     int textStart,
         *                     String pattern)
         *
         * This test case represents equivalence class of test inputs. The library starts searching for the pattern
         * in the target string from the given index. This particular test case passes index beyond the length of
         * the target string
         *
         * Input:
         * - a non-empty target string
         * - a non-empty and a substring of target string as pattern
         * - a negative start index
         *
         * Expected Behavior:
         * - No match
         */
        String target = "thequickbrown";
        String pattern = getSamplePattern("quickb");
        int startIndex = target.length();

        System.out.print("  target: " + target + ", pattern: " + pattern + ", start-index: " + startIndex);

        int location = stringSearch.searchString(target, startIndex, pattern);
        Assert.assertEquals(-1, location);

        System.out.println(" (PASSED)");
    }

    void testMatchingOccursAtStartIndex() throws AssertionFailureException {

    }

    void testEndIndexLessThanStartIndex() throws AssertionFailureException {
        /**
         *
         */

        String target = "thequickbrown";
        String pattern = getSamplePattern("quickb");

        int startIndex = 2;
        int endIndex = 1;

        System.out.print("  target: " + target + ", pattern: " + pattern + ", start-index: " + startIndex);

        int location = stringSearch.searchString(target, startIndex, endIndex, pattern);
        Assert.assertEquals(-1, location);

        System.out.println(" (PASSED)");
    }

    void testMatchingSingleCharacterAtStartIndexAndEndIndex() throws AssertionFailureException {
        /**
         *
         */

        String target = "thequickbrown";
        String pattern = getSamplePattern("e");

        int startIndex = 2;
        int endIndex = 2;

        System.out.print("  target: " + target + ", pattern: " + pattern + ", start-index: " + startIndex + ", end-index: " + endIndex);

        int location = stringSearch.searchString(target, startIndex, endIndex, pattern);

        Assert.assertEquals(2, location);

        System.out.println(" (PASSED)");
    }



    void testMatchingAtMiddle() throws AssertionFailureException {
        /**
         * Test 5
         *
         * Technique used: Equivalence Input Partitioning.
         *
         * This test case represents equivalence class of test inputs
         * to any implementation of StringSearch.
         *
         * The pattern is found in the target string
         *
         * - a target string
         * - a pattern that matches somewhere at the target string
         * - the pattern is found in the string
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("quick");

        match(str, pattern, 3);

    }


    void testMatchingAtBeginning() throws AssertionFailureException {
        /**
         * Test 6
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching at the beginning of the target string.
         *
         * Input:
         * - a target string
         * - a pattern that should match at the beginning of the string
         * Expected behaviour:
         * - the pattern is found at position 0
         *
         */

        String str = "thequickbrown";
        String pattern = getSamplePattern("thequick");

        match(str, pattern, 0);
    }

    void testMatchingAtEnd() throws AssertionFailureException {

        /**
         * Test 7
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching at the end of the target string.
         *
         * Input:
         * - a target string
         * - a pattern that matches at the end of the string
         * Expected behaviour:
         * - the pattern found position is the difference between two input lengths
         *
         */

        String str = "thequickbrown";
        String pattern = getSamplePattern("brown");

        match(str, pattern, str.length() - pattern.length());
    }

    void testStartMatchingFromSomewhereInTheMiddleOfTheTarget() throws AssertionFailureException{
        /**
         * Test 8
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
         * - a negative start index
         *
         * Expected Behavior:
         * - No match
         */
        String target = "thequickbrownquickbrown";
        String patternBeforeAltering = "quickb";
        String pattern = getSamplePattern(patternBeforeAltering);

        int startIndex = target.indexOf(patternBeforeAltering) + 1;
        int expectedIndex = target.indexOf(patternBeforeAltering, startIndex);

        System.out.print("  target: " + target + ", pattern: " + pattern);

        int location = stringSearch.searchString(target, startIndex, pattern);
        Assert.assertEquals(expectedIndex, location);

        System.out.println(" (PASSED)");
    }

    void testFullMatch() throws AssertionFailureException {
        /**
         * Test 9
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching between exact same string.
         *
         * Input:
         * - a target string
         * - a pattern that is same as the input string (may have wildcard or case variant)
         * Expected behaviour:
         * - the pattern is found at position 0
         *
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("thequickbrownfox");

        match(str, pattern, 0);
    }

    void testOverMatch() throws AssertionFailureException {
        /**
         * Test 10
         *
         * Technique used: boundary value analysis.
         *
         * This test case tries to match between two strings where target is a substring of the pattern.
         *
         * Input:
         * - a target string
         * - a pattern that is longer than and contains the substring
         *
         * - the pattern not found in the target
         *
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("thequickbrownfoxjump");

        match(str, pattern, -1);
    }

    void testSpecialCharacterMatch() throws AssertionFailureException {
        /**
         * Test 11
         *
         * Technique used: equivalence test partitioning & boundary value.
         * - Equivalence in the sense this particular test uses special characters.
         * - Boundary in the sense all characters in the pattern are special characters; testing whether
         *     library can handle all these
         *
         * This test case runs matching between strings with special characters
         *
         * Input:
         * - a target string with special character
         * - a pattern of special characters that matches the target string
         *
         * - the pattern found at the target
         *
         */

        String str = "hello_+&^* ()-=!@#world";
        String pattern = "&^* ()";

        int location = stringSearch.searchString(str, pattern);

        Assert.assertEquals(str.indexOf('&'), location);
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

        String str = "Garbage Starting Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String pattern = getSamplePattern("Lorem ipsum dolor sit amet, cons");

        match(str, pattern, 17);
    }

    void testMatchingAtLastCharacter() throws AssertionFailureException {
        /**
         * Test 13
         *
         * Technique used: boundary value of output
         *
         * Input:
         * - a very long target string ends with a special character
         * - a pattern string contains only the special character
         * Expected behaviour:
         * - returns matching index as one less than target length
         */

        String target = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ac ligula sed lorem posuere tristique. Donec commodo accumsan mauris. Nunc in est ac ligula iaculis pretium. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam cursus pharetra eros, at consectetur lacus laoreet id. Vestibulum consectetur scelerisque laoreet. Nam ac egestas nulla. Fusce ac nunc libero, vel pretium magna. Vestibulum vehicula hendrerit urna quis sagittis.\n" +
                "\n" +
                "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla arcu lorem, pharetra at malesuada ac, molestie eget erat. Proin at nisl urna. Etiam posuere, magna ac eleifend vestibulum, nibh quam vulputate justo, quis faucibus leo massa ac mauris. Phasellus bibendum, nibh in iaculis hendrerit, nibh dolor tristique nisl, vel dapibus diam massa id tellus. Donec iaculis sapien eget mauris semper mollis interdum eros iaculis. Proin at lacus tellus, eget molestie erat. Ut tincidunt erat et dolor fringilla posuere. Aliquam dignissim lacinia nisi, non fringilla quam tempor nec. Suspendisse ac est in turpis gravida auctor. Ut tempor sem in dui laoreet sed pretium mauris ornare. Suspendisse quis nisi elit.\n" +
                "\n" +
                "Sed diam elit, pretium eget dictum et, convallis tempus augue. Nunc nec rutrum lorem. Aliquam placerat placerat tellus, nec facilisis sapien hendrerit vel. Etiam viverra gravida orci, vitae condimentum nulla lacinia nec. Pellentesque ultrices dignissim tellus, id sagittis massa molestie ac. Morbi ac libero lectus. Vestibulum hendrerit orci vitae dui scelerisque bibendum. Praesent sagittis luctus vestibulum. In ultrices pellentesque nisi vitae euismod. Ut semper porttitor turpis, id pharetra leo commodo eget. Aliquam pellentesque ultricies mollis. Ut gravida turpis nec arcu rhoncus gravida. Nullam id nibh congue elit luctus porttitor vel eu risus. Proin fringilla faucibus arcu, eu semper dui laoreet sodales. Mauris ultricies faucibus sapien quis ultrices.\n" +
                "\n" +
                "Morbi mollis hendrerit ultrices. Pellentesque non eleifend orci. Sed congue felis eu leo fringilla vestibulum. Nullam consectetur, eros mattis ultrices tempor, mauris tellus aliquet enim, vitae vestibulum nibh lectus a libero. Nulla pellentesque consectetur vestibulum. Sed sed leo ipsum, ut rutrum purus. Maecenas in consectetur est. Maecenas sit amet ante ut augue sodales scelerisque. Maecenas semper, turpis vitae eleifend egestas, odio nulla tristique dolor, sit amet tempus lectus elit blandit urna. Vivamus varius consequat diam non placerat. Quisque vulputate eleifend laoreet. Donec gravida arcu ac dui dictum aliquam. Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "\n" +
                "Ut a nulla quis leo condimentum pretium eget ac sem. Sed orci sem, ultricies ac vestibulum nec, consequat quis nisi. Nam accumsan mauris et elit ultrices rutrum. Etiam quis mauris eu turpis ultricies ultrices. Nunc eu arcu hendrerit enim sagittis tristique sed a dolor. Duis sem libero, pulvinar vel condimentum vitae, ultrices vulputate orci. Vestibulum laoreet egestas neque vitae viverra. In hac habitasse platea dictumst.$";
        String pattern = "$";

        match(target, pattern, target.length()-1);

    }

}
