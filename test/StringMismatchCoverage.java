import com.eaio.stringsearch.*;

/**
 * The test cases to cover {@link MismatchSearch}. The only difference of the methods  with other {@link StringSearch}
 * methods is these methods takes another parameter n; the number of possible mismatches
 */
public class StringMismatchCoverage implements StringSearchTests {
    MismatchSearch mismatchSearch;

    public StringMismatchCoverage(MismatchSearch mismatchSearch) {
        this.mismatchSearch = mismatchSearch;
    }


    /**
     * Pass all possible bytes to {@link MismatchSearch#searchBytes}
     */
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

    /**
     * Similar to {@link StringMatchCoverage#testWithProcessed()}. This time with extra parameter k
     */
    public void testWithProcessed() {
        String target = "fdsak";
        String pattern = "fpiou";

        String targetZeroLength = "";
        int k = 5;

        Object processedString = mismatchSearch.processString(pattern, k);
        Object processedCharacters = mismatchSearch.processString(pattern, k);
        Object processedBytes = mismatchSearch.processBytes(pattern.getBytes(), k);

        mismatchSearch.searchString(targetZeroLength, 0, pattern, k);
        mismatchSearch.searchString(targetZeroLength, pattern, processedString, k);

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
}
