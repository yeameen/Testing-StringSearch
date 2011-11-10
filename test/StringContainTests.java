import com.eaio.stringsearch.BNDM;
import com.eaio.stringsearch.StringSearch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/1/11
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringContainTests {

    private StringSearch stringSearch;

    private boolean isIgnoreCase;
    private boolean isWildcards;

    public StringContainTests(StringSearch stringSearch, boolean isIgnoreCase, boolean isWildcards) {
        this.stringSearch = stringSearch;
        this.isIgnoreCase = isIgnoreCase;
        this.isWildcards = isWildcards;
    }

    private int getRandomIntWithinRange(int range) {
        return (int)(Math.random() * range);
    }

    private String getSamplePattern(String input) {
        String output = new String(input);
        int length = input.length();

        if(isIgnoreCase) {
            int numberOfCharactersToReplace = getRandomIntWithinRange(length);
            char[] chars = input.toCharArray();
            for(int i = 0; i <= numberOfCharactersToReplace; i++) {
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
            char[] chars = input.toCharArray();
            for(int i = 0; i <= numberOfCharactersToReplace; i++) {
                int randomPosition = getRandomIntWithinRange(length);
                chars[i] = '.';
            }
            output = new String(chars);
        }

        return output;
    }

    void match(String target, String pattern, int expectedPosition) {
        System.out.println("target: " + target + ", pattern: " + pattern);
        int location = stringSearch.searchString(target, pattern);

        Assert.assertEquals(expectedPosition, location);
    }

    void testMismatch() {
        // Technique used: Equivalence Partitioning.

        // This test case represents the equivalence class of test inputs
        // to the BNDM implementation of StringSearch.searchString that contain:

        //  - a non-null pattern
        //  - a non-null string
        //  - the pattern is NOT found in the string.

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("jumpover");

        match(str, pattern, -1);
    }

//    void testJavaStringSearchWithEmptyPattern() {
//
//        String str = "something";
//        String pattern = "";
//
//        int location = str.indexOf(pattern);
//
//        System.out.println("It doesn't crash!");
//
//    }
//
//    void testJavaRegexWithEmptyPattern() {
//        String str = "something";
//        String pattern = "";
//
//        // Let's see if it crashes
//        Pattern p = Pattern.compile("");
//        Matcher m = p.matcher("helloworld");
//
//        boolean b = m.matches();
//
//        System.out.println("It doesn't crash!");
//    }
//
//
//
//    void testEmptyPattern() {
//        /**
//         * Test with empty pattern
//         */
//
//        String str = "something";
//        String pattern = "";
//
//        int location = stringSearch.searchString(str, pattern);
//        Assert.assertEquals(-1, location);
//    }

    /**
     * Test with a pattern longer than 32 characters. It ignores the rest
     */
    void testLongerThanAllowedPattern() {
        /**
         * Technique used: Equivalence Partitioning.
         *
         *
         *
         */

        //

        // This test case represents the equivalence class of test inputs

        // Input:
        //  - a pattern with more than 32 characters
        //  - a string that matches first 32 characters of the pattern
        // Output:
        //  - the index of where the matching starts

        String str = "Garbage Starting Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String pattern = getSamplePattern("Lorem ipsum dolor sit amet, cons8");

        match(str, pattern, 17);
    }

    void testMatchingAtBeginning() {
        /**
         * Technique used: boundary value matching.
         *
         * Input:
         * - a target string
         * - a pattern that should match at the beginning of the string
         * Output:
         *
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("thequick");

        match(str, pattern, 0);
    }

    void testMatchingAtMiddle() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches somewhere at the middle of the string
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("quick");

        match(str, pattern, 3);

    }

    void testMatchingAtEnd() {

        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches at the end of the string
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("fox");

        match(str, pattern, 13);
    }

    void testFullMatch() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches completely with the string
         */

        String str = "thequickbrownfox";
        String pattern = getSamplePattern("thequickbrownfox");

        match(str, pattern, 0);

    }

    void testOverMatch() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches partially
         */

        String str = "thequickbrownfox";
        String pattern = ("thequickbrownfoxjump");

        match(str, pattern, -1);
    }

    void testSpaceMatch() {
        String str = "hello world";
        String pattern = " ";

        int location = stringSearch.searchString(str, pattern);

        Assert.assertEquals(5, location);
    }
}
