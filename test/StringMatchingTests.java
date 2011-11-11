import com.eaio.stringsearch.BNDM;
import com.eaio.stringsearch.StringSearch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chowdhury Ashabul Yeameen
 * @since 11/1/11
 */
public class StringMatchingTests {

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
        // Technique used: Equivalence Partitioning.

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
         * Technique used: Equivalence Partitioning.
         *
         * This test case represents equivalence class of test inputs
         * to any implementation of StringSearch. Since the documentation says no parameter may be null - the program
         * is expected to through Exception
         *
         *
         * Any illegal input null should throw Exception
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

        String target = null;
        String pattern = null;

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        pattern = "quick";

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        target = "thequickbrown";
        pattern = null;

        try {
            stringSearch.searchString(target, pattern);
        } catch (NullPointerException e) {
            numOfExceptions++;
        }

        Assert.assertIsTrue(3 == numOfExceptions, "Illegal input(s) should throw exception");
    }

    void testEmptyPattern() throws  AssertionFailureException {
        /**
         * Test 2
         * Technique used: Equivalence Partitioning.
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


    void testMatchingAtMiddle() throws AssertionFailureException {
        /**
         * Test 3
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
         * Test 4
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching at the beginning of the target string.
         *
         * Input:
         * - a target string
         * - a pattern that should match at the beginning of the string
         * Output:
         * - the pattern is found at position 0
         *
         */

        String str = "thequickbrown";
        String pattern = getSamplePattern("thequick");

        match(str, pattern, 0);
    }

    void testMatchingAtEnd() throws AssertionFailureException {

        /**
         * Test 5
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching at the end of the target string.
         *
         * Input:
         * - a target string
         * - a pattern that matches at the end of the string
         * Output:
         * - the pattern found position is the difference between two input lengths
         *
         */

        String str = "thequickbrown";
        String pattern = getSamplePattern("brown");

        match(str, pattern, str.length() - pattern.length());
    }

    void testFullMatch() throws AssertionFailureException {
        /**
         * Test 6
         *
         * Technique used: boundary value analysis.
         *
         * This test case checks string matching between exact same string.
         *
         * Input:
         * - a target string
         * - a pattern that is same as the input string (may have wildcard or case variant)
         * Output:
         * - the pattern is found at position 0
         *
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("thequickbrownfox");

        match(str, pattern, 0);
    }

    void testOverMatch() throws AssertionFailureException {
        /**
         * Test 7
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
         * Test 8
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

    void testLongerThanAllowedPattern() throws AssertionFailureException {
        /**
         * Test 9
         *
         * Technique used: boundary value.
         *
         * Input:
         *  - a pattern with more than 32 characters
         *  - a target string that matches first 32 characters of the pattern
         * Output:
         *  - the index of where the matching starts
         *
         *  Note: This test fails on BoyerMoore variants
         */

        String str = "Garbage Starting Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String pattern = getSamplePattern("Lorem ipsum dolor sit amet, cons8");

        match(str, pattern, 17);
    }

}
