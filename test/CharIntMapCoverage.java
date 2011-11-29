import com.eaio.stringsearch.BNDM;
import com.eaio.stringsearch.CharIntMap;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/17/11
 * Time: 12:04 AM
 * To change this template use File | Settings | File Templates.
 */
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
        String nonEmptyString = "asdf";
        CharIntMap nonEmptyCharIntMap = getCharIntMap(nonEmptyString);
        CharIntMap equivalentCharIntMap = getCharIntMap(STRING_CHAR_INT);

        charIntMap.equals(charIntMap);
        charIntMap.equals(emptyCharIntMap);
        charIntMap.equals(nonEmptyCharIntMap);
        charIntMap.equals(nonEmptyString);
    }

    public void testHashcode() {
        charIntMap.hashCode();
    }



    public void testWriteExternal() throws Exception {
        // The persistence can be also implemented with filesystem
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

    public void testReadExternal() throws IOException {
        // Open inputstream from storage
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(storage);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        new CharIntMap().readExternal(objectInputStream);
        objectInputStream.close();
    }
}
