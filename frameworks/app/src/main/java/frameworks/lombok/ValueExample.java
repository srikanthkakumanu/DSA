package frameworks.lombok;

import lombok.experimental.NonFinal;
import lombok.Value;
import lombok.ToString;

/**
 * @Value:
 *  - @Value is shorthand for @ToString, @EqualsAndHashCode, @AllArgsConstructor, @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) 
 *    @Getter, Except that explicitly including the implementation of any of the relevant methods basically means that part will not be generated 
 *    and no warning will be issued.
 *  - @Value is the immutable variant of @Data; all fields are made private and final by default, and setters are not generated. The class itself 
 *    is also made final by default. 
 *  - Like @Data, useful toString(), equals() and hashCode() methods are also generated, each field gets a getter method, and a constructor that 
 *    covers every argument (except final fields that are initialized in the field declaration) is also generated.
 */
@Value public class ValueExample {
  String name;
  @NonFinal int age;
  double score;
  protected String[] tags;
  
  @ToString(includeFieldNames=true)
  @Value(staticConstructor="of")
  public static class Exercise<T> {
    String name;
    T value;
  }
}
