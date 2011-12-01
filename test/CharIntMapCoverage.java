import com.eaio.stringsearch.BNDM;
import com.eaio.stringsearch.CharIntMap;

import java.io.*;

public class CharIntMapCoverage implements StringSearchTests {

    private CharIntMap charIntMap;
    private final String STRING_CHAR_INT = "abracadebra";
    private byte[] storage;

//    required if you use filesystem for read/write
//    File temp;


    private void init() {
        this.charIntMap = getCharIntMap(STRING_CHAR_INT);
    }

    private CharIntMap getCharIntMap(String input) {
        return (CharIntMap)new BNDM().processString(input);
    }

    public CharIntMapCoverage() {
        init();
    }

    public void testAddingCharacters() {
        char a = 'a';

        charIntMap.set(a, 0);
        charIntMap.get(a);
    }

    public void testToString() {
        charIntMap.toString();
        charIntMap.toStringBuffer(new StringBuffer());
    }

    public void testGettingLength() {
        charIntMap.getExtent();
    }

    public void testGettingHighest() {
        charIntMap.getHighest();
    }

    public void testGettingLowest() {
        charIntMap.getLowest();
    }

    public void testEquality() {
        CharIntMap emptyCharIntMap = new CharIntMap();
        CharIntMap emptyCharIntMap2 = new CharIntMap();
        String nonEmptyString = "sdf";
        CharIntMap nonEmptyCharIntMap = getCharIntMap(nonEmptyString);
        CharIntMap equivalentCharIntMap = getCharIntMap(STRING_CHAR_INT);
        CharIntMap differentDefaultValueCharIntMap = new CharIntMap(5, 'a', 1);

        charIntMap.equals(charIntMap);
        charIntMap.equals(emptyCharIntMap);
        charIntMap.equals(nonEmptyCharIntMap);
        charIntMap.equals(nonEmptyString);
        charIntMap.equals(equivalentCharIntMap);
        charIntMap.equals(differentDefaultValueCharIntMap);
        emptyCharIntMap.equals(emptyCharIntMap2);
    }

    public void testHashcode() {
        charIntMap.hashCode();
    }



    public void testWriteExternal() throws Exception {

//        The persistence can be also implemented with filesystem
//        temp = File.createTempFile("temp", "data");
//        temp.deleteOnExit();
//        FileOutputStream fileOutputStream = new FileOutputStream(temp);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        charIntMap.writeExternal(objectOutputStream);
        objectOutputStream.close();

        storage = outputStream.toByteArray();

    }

    public void testWriteExternalForEmptyCharintmap() throws Exception {
        // write an empty char int map; just to increase coverage

        CharIntMap emptyCharIntMap = new CharIntMap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        emptyCharIntMap.writeExternal(objectOutputStream);
    }

    public void testReadExternal() throws IOException {

        // Open inputstream from storage
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(storage);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        new CharIntMap().readExternal(objectInputStream);
        objectInputStream.close();
    }
}
