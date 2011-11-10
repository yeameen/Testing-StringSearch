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

    public StringContainTests(StringSearch stringSearch) {
        this.stringSearch = stringSearch;
    }

    void testMismatch() {
        // Technique used: Equivalence Partitioning.

        // This test case represents the equivalence class of test inputs
        // to the BNDM implementation of StringSearch.searchString that contain:

        //  - a non-null pattern
        //  - a non-null string
        //  - the pattern is NOT found in the string.

//        StringSearch ss = new BNDM();
        String str = "helloworld";
        String pattern = "se6367";

        int location = stringSearch.searchString(str, pattern);

        Assert.assertEquals(-1, location);

    }

    void testJavaStringSearchWithEmptyPattern() {

        String str = "something";
        String pattern = "";

        int location = str.indexOf(pattern);

        System.out.println("It doesn't crash!");

    }

    void testJavaRegexWithEmptyPattern() {
        String str = "something";
        String pattern = "";

        // Let's see if it crashes
        Pattern p = Pattern.compile("");
        Matcher m = p.matcher("helloworld");

        boolean b = m.matches();

        System.out.println("It doesn't crash!");
    }



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
        String pattern = "Lorem ipsum dolor sit amet, con8";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(str.indexOf(pattern), location);
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

        String str = "helloworld";
        String pattern = "hello";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(str.indexOf(pattern), location);

    }

    void testMatchingAtMiddle() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches somewhere at the middle of the string
         */

        String str = "helloworld";
        String pattern = "llowo";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(str.indexOf(pattern), location);


    }

    void testMatchingAtEnd() {

        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches at the end of the string
         */

        String str = "helloworld";
        String pattern = "world";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(str.indexOf(pattern), location);

    }

    void testFullMatch() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches completely with the string
         */

        String str = "helloworld";
        String pattern = "helloworld";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(0, location);


    }

    void testOverMatch() {
        /**
         * Technique used: Equivalence Input Partitioning.
         *
         * argument - a target string
         * argument - a pattern that matches partially
         */

        String str = "helloworld";
        String pattern = "helloworldhello";

        int location = stringSearch.searchString(str, pattern);
        Assert.assertEquals(-1, location);

    }

    void testSpaceMatch() {
        String str = "hello world";
        String pattern = " ";

        int location = stringSearch.searchString(str, pattern);

        Assert.assertEquals(5, location);

    }

}
