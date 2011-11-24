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
    private byte[] storage;
    File temp;

    private void init() {
        this.charIntMap = (CharIntMap)new BNDM().processString("abracadebra");
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

//    public void testClone() throws Exception {
//        CharIntMap charIntMap = new CharIntMap();
//        this.charIntMap.equals(charIntMap);
//        charIntMap.writeExternal(new ObjectOutputStream(System.out));
////        charIntMap.toString();
//    }

    public void testWriteExternal() throws Exception {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000)
//        init();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//        objectOutputStream.write(0);

        temp = File.createTempFile("temp", "data");
//        temp.deleteOnExit();
        OutputStream outputStream = new FileOutputStream(temp);
        charIntMap.writeExternal(new ObjectOutputStream(outputStream));
        outputStream.close();

//        storage = outputStream.toByteArray();
//        System.out.println("Storage value: " + new String(storage));
//        outputStream.close();

    }

    public void testReadExternal() throws IOException {
        InputStream inputStream = new FileInputStream(temp);
        charIntMap.readExternal(new ObjectInputStream(inputStream));
        inputStream.close();
    }
}
