package basics;

/**
 * Enums introduced in Java 4/5.
 * 1. Enums ensures type safety for
 * 2. Enums can have fields, constructors, methods, abstract methods.
 * 3. Enums can be traversed. 
 * 4. Enums can implement multiple interfaces but cannot extend any class because
 *    internally it extends java.lang.Enum class.
 * 5. Constructor of enum type is private meaning it cannot be instantiated. 
 *      If you don't declare private constructor, compiler internally creates 
 *      a private constructor. 
 * 
 * 
 */
public class Enums {

    public enum DAYS {
        SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);

        private int value;
        private DAYS(int value) {
            this.value = value;
        }
    }
    public static void main(String[] args) {
        for (DAYS d : DAYS.values()) {
            System.out.println("[name=" + d.name() + ", value=" + d.value + ", ordinal(index)=" + d.ordinal() + "] "); // ordinal is index of enum value
        }
    }
}
