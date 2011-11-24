import com.eaio.stringsearch.*;

public class StringMatchCoverage implements StringSearchTests {
    StringSearch stringSearch;

    public StringMatchCoverage(StringSearch stringSearch) {
        this.stringSearch = stringSearch;
    }

    public void testSearchBytesMismatch() {
        String target = "fdlaskj";
        String pattern = "fda";

        stringSearch.searchBytes(target.getBytes(), pattern.getBytes());
    }


    public void testSearchBytesMatch() {
        String target = "fdlaskj";
        String pattern = "las";

        stringSearch.searchBytes(target.getBytes(), pattern.getBytes());
    }

    public void testSearchWithMixedCase() {
        String target = "fdlaskj";
        String pattern = "ABC";

        stringSearch.searchBytes(target.getBytes(), pattern.getBytes());
    }

    public void testSearchBytesWithLongInput() {
        String target = "dfas;lkjfsda;ksfdjlksdajlsdafjsdakjfslkj";
        String pattern = "dfaslkjfsdak afdskjfsdalk asfdkjsfdn afsdlkjfsda";

        stringSearch.searchBytes(target.getBytes(), pattern.getBytes());
    }

    public void testByteWithAbove127() {
        byte[] targetBytes = {(byte)128, 1};
        byte[] patternBytes = {(byte)128, (byte)255};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testByteWithUppercase() {
        byte[] targetBytes = {1, 2, 3};
        byte[] patternBytes = {(byte)93, (byte)128, (byte)140};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testPatternWithAllPossibleBytes() {
        byte[] targetBytes = new byte[32];
        byte[] patternBytes = new byte[32];

        for(int i = -128; i <= 127; i += 32) {
            for(int j = i, k = 0; k < 32; j++, k++) {
                targetBytes[k] = patternBytes[k] = (byte)j;
            }

            stringSearch.searchBytes(targetBytes, patternBytes);
        }
    }

    public void testProcessString() {
        String pattern = "world";

        stringSearch.processString(pattern);
    }

    public void testWildcardsProcessString() {
        BNDMWildcards stringSearch = new BNDMWildcards();
        String pattern = "wo?ld";
        char wildChar = '?';
        stringSearch.processString(pattern, wildChar);

    }

    public void testByteWithLengthOneMatch() {
        byte[] targetBytes = {2};
        byte[] patternBytes = {2};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testByteWithLengthOneMismatch() {
        byte[] targetBytes = {1};
        byte[] patternBytes = {2};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testByteWithLengthTwoMatch() {
        byte[] targetBytes = {1, 2};
        byte[] patternBytes = {1, 2};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testByteWithLengthTwoMismatch() {
        byte[] targetBytes = {1, 3};
        byte[] patternBytes = {2, 3};

        stringSearch.searchBytes(targetBytes, patternBytes);
    }

    public void testCharacterWithLengthTwoMatch() {
        char[] targetBytes = {1, 2};
        char[] patternBytes = {1, 2};

        stringSearch.searchChars(targetBytes, patternBytes);
    }

    public void testCharacterWithLengthTwoMismatch() {
        char[] targetBytes = {1, 2};
        char[] patternBytes = {1, 3};

        stringSearch.searchChars(targetBytes, patternBytes);
    }

    public void testCallingHashcode() {
        stringSearch.hashCode();
    }

    public void testCallingToString() {
        stringSearch.toString();
    }

    public void testUseReflection() {
        StringSearch.usesReflection();
    }

    public void testWithProcessed() {
        String target = "fdsak";
        String pattern = "fpiou";
        String targetZero = "";

        Object processedString = stringSearch.processString(pattern);
        Object processedCharacters = stringSearch.processString(pattern);
        Object processedBytes = stringSearch.processBytes(pattern.getBytes());

//        stringSearch.searchString(targetZero, pattern);
//        stringSearch.searchString(targetZero, 0, pattern);
        stringSearch.searchString(targetZero, pattern, processedString);
//        stringSearch.searchString(targetZero, 0, pattern, processedString);
//        stringSearch.searchString(targetZero, 0, pattern.length(), pattern, processedString);


        stringSearch.searchString(target, pattern);
        stringSearch.searchChars(target.toCharArray(), pattern.toCharArray());
        stringSearch.searchBytes(target.getBytes(), pattern.getBytes());

        stringSearch.searchString(target, 0, pattern);
        stringSearch.searchChars(target.toCharArray(), 0, pattern.toCharArray());
        stringSearch.searchBytes(target.getBytes(), 0, pattern.getBytes());


        stringSearch.searchString(target, pattern, processedString);
        stringSearch.searchChars(target.toCharArray(), pattern.toCharArray(), processedString);
        stringSearch.searchBytes(target.getBytes(), pattern.getBytes(), processedBytes);

        stringSearch.searchString(target, 0, pattern, processedString);
        stringSearch.searchChars(target.toCharArray(), 0, pattern.toCharArray(), processedCharacters);
        stringSearch.searchBytes(target.getBytes(), 0, pattern.getBytes(), processedBytes);

//        stringSearch.searchString(target, 0, pattern.length(), pattern, processedString);
        stringSearch.searchChars(target.toCharArray(), 0, pattern.length(), pattern.toCharArray());
        stringSearch.searchBytes(target.getBytes(), 0, pattern.length(), pattern.getBytes());

    }
    public void testCallingDifferentStringSearchInstance() {
        StringSearch differentStringSearch = new BNDM();
        String target = "hello";
        String pattern = "fads";

        stringSearch.equals(differentStringSearch);
        stringSearch.equals(stringSearch);
        stringSearch.equals(null);
    }

    public void testWithMismatchSearch() {
        MismatchSearch mismatchSearch = new ShiftOrMismatches();
        String target = "hello";
        String pattern = "fads";
    }
}
