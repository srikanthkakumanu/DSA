package frameworks.lombok.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.val;
import lombok.NonNull;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * Lombok Library/Framework Usage
 * 
 * 1. @Getter and @Setter: 
 *      - We can annotate any field with @Getter and @Setter to allow lombok to generate getter/setter functionality.
 *      - These annotations can be used both the levels i.e. field level and class level.
 *      - The generated methods are `public` by default unless we specify the AccessLevel (PUBLIC, PROTECTED, PACKAGE, PRIVATE).
 * 
 * 2. @NoArgsConstructor: 
 *      - It generates a constructor with no arguments.
 * 3. @ToString: 
 *      - Generates toString() method.
 *      - We can exclude certain fields if we want by using ToString.Exclude
 * 4. @EqualsAndHashCode:
 *      - Generates equals() and hashcode() methods.
 *      - We can exlcude certain fields if we want by using EqualsAndHashCode.Exclude
 * 5. @Data:
 *      - It is a shortcut annotation to bundle @ToString, @Getter, @Setter, @EqualsAndHashCode 
 *          and @NoArgsConstructor annotations into a single annotation.
 * 6. @Builder:
 *      - Builder pattern automatically produce the code required i.e. builder() method.
 *      - No getters available.
 *      - It can be placed on a class, or on a constructor, or on a method.
 * 7. @Val:
 *      - Use val annotation for declaring the type of variable like we do in Java. 
 * 8. @NonNull:
 *      - We can use @NonNull annotation on the parameter of constructor or a method to generate null check.
 * 9. @Log:
 *      - We can write the @Log option on a class (whichever one corresponds to the logging method we are using); 
 *         we then have a final static log field initialized as is the commonly prescribed way of logging the 
 *         framework we are using, which we can then use to write log statements.
 *      - Other log annotations: @Log, @CommonsLog, @Flogger, @JBossLog, @Log4j, @Log4j2, @XSlf4j, @CustomLog
 *      - @CustomLog lets you add any logger by configuring how to create them with a config key.
 * 10. @Synchronized:
 *      - @Synchronized is a safer type of the synchronized method modifier. This annotation can be use on 
 *          methods (both instance and static) and it'll autogenerate private, unexposed field that your 
 *          implementation will use for locking.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
public class Notes {

    private @NonNull Integer id;
    private String comment;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private char[] sharedSecret;

    public void some() {
        val allNotes = new ArrayList<String>();
    }
    
    @Synchronized
    public void addComment(@NonNull Integer id, String comment) {
        // thread-safe code...
    }
}