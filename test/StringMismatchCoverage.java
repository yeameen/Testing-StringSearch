import com.eaio.stringsearch.*;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/15/11
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringMismatchCoverage implements StringSearchTests {
    MismatchSearch mismatchSearch;

    public StringMismatchCoverage(MismatchSearch mismatchSearch) {
        this.mismatchSearch = mismatchSearch;
    }


    public void testPatternWithAllPossibleBytes() {
        byte[] targetBytes = new byte[32];
        byte[] patternBytes = new byte[32];
        int n = 1;

        for(int i = -128; i <= 127; i += 32) {
            for(int j = i, k = 0; k < 32; j++, k++) {
                targetBytes[k] = patternBytes[k] = (byte)j;
            }

            mismatchSearch.searchBytes(targetBytes, patternBytes, n);
        }
    }

    public void testWithProcessed() {
        String target = "fdsak";
        String pattern = "fpiou";

        String targetZero = "";
        int k = 5;

        Object processedString = mismatchSearch.processString(pattern, k);
        Object processedCharacters = mismatchSearch.processString(pattern, k);
        Object processedBytes = mismatchSearch.processBytes(pattern.getBytes(), k);

//        mismatchSearch.searchString(targetZero, pattern, k);
        mismatchSearch.searchString(targetZero, 0, pattern, k);
        mismatchSearch.searchString(targetZero, pattern, processedString, k);
//        mismatchSearch.searchString(targetZero, 0, pattern, processedString, k);



        mismatchSearch.searchString(target, pattern, k);
        mismatchSearch.searchChars(target.toCharArray(), pattern.toCharArray(), k);
        mismatchSearch.searchBytes(target.getBytes(), pattern.getBytes(), k);

        mismatchSearch.searchString(target, 0, pattern, k);
        mismatchSearch.searchChars(target.toCharArray(), 0, pattern.toCharArray(), k);
        mismatchSearch.searchBytes(target.getBytes(), 0, pattern.getBytes(), k);


        mismatchSearch.searchString(target, pattern, processedString, k);
        mismatchSearch.searchChars(target.toCharArray(), pattern.toCharArray(), processedString, k);
        mismatchSearch.searchBytes(target.getBytes(), pattern.getBytes(), processedBytes, k);

        mismatchSearch.searchString(target, 0, pattern, processedString, k);
        mismatchSearch.searchChars(target.toCharArray(), 0, pattern.toCharArray(), processedCharacters, k);
        mismatchSearch.searchBytes(target.getBytes(), 0, pattern.getBytes(), processedBytes, k);

        mismatchSearch.searchChars(target.toCharArray(), 0, pattern.length(), pattern.toCharArray(), k);
        mismatchSearch.searchBytes(target.getBytes(), 0, pattern.length(), pattern.getBytes(), k);

    }
    public void testCallingDifferentStringSearchInstance() {
        StringSearch differentStringSearch = new BNDM();
        String target = "hello";
        String pattern = "fads";

        mismatchSearch.equals(differentStringSearch);
        mismatchSearch.equals(mismatchSearch);
        mismatchSearch.equals(null);
    }

    public void testWithMismatchSearch() {
        MismatchSearch mismatchSearch = new ShiftOrMismatches();
        String target = "hello";
        String pattern = "fads";
    }
}
