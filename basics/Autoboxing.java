package basics;

/**
 * Auto boxing and unboxing introduced in Java 4/5.
 */
public class Autoboxing {
    public static void main(String[] args) {
        int a = 10;
        Integer i = new Integer(a); // boxing 
        Integer j = 20; // boxing

        Integer k = new Integer(30); 
        int b = k; // unboxing

        System.out.println("[i=" + i + ", j=" + j + ", k=" + k + ", b=" + b + "]");
    }
}
