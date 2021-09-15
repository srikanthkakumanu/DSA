package frameworks.lombok;

import lombok.Cleanup;
import lombok.SneakyThrows;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


/**
 * @SneakyThrows annotation used to sneakily throw checked exceptions without actually declaring this in your method's throws clause.
 * Caution Note: 
 *      The code generated by lombok will not ignore, wrap, replace, or otherwise modify the thrown checked exception; it simply fakes 
 *      out the compiler. Be conscious that it is impossible to explicitly catch sneakily thrown checked types, as javac will not 
 *      let you write a catch block for an exception type that no method call in the try body declares as thrown. Let this serve 
 *      as an alert that without some deliberation, you should not use the @SneakyThrows mechanism.
 * 
 * @Cleanup:
 *  Java 7 implemented the try-with-resources block to ensure that instances of anything implementing java.lang.AutoCloseable are 
 *  released upon exiting your resources. In Lombok, @Cleanup annotation offers an alternative way to do this, and more flexibly. 
 *  Use it with every local variable that you want to make sure it releases it's resources. Simply use @Cleanup annotation which 
 *  will call its close() method to release it's resources.
 * 
 */
public class OtherLomboks {

    @SneakyThrows
    public String doSneakyThrows() {
        try (InputStream input = this.getClass().getResourceAsStream("note.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    @SneakyThrows
    public void doCleanup() { 
        @Cleanup
        InputStream input = this.getClass().getResourceAsStream("note.txt");
    }
}
