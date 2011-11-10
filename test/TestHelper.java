/**
 * Created by IntelliJ IDEA.
 * User: yeameen
 * Date: 11/10/11
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestHelper {
    public static String splitCamelCase(String s) {
       return s.replaceAll(
          String.format("%s|%s|%s",
             "(?<=[A-Z])(?=[A-Z][a-z])",
             "(?<=[^A-Z])(?=[A-Z])",
             "(?<=[A-Za-z])(?=[^A-Za-z])"
          ),
          " "
       );
    }
}
