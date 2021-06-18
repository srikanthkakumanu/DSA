package basics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * var is introduced in Java 10
 * 1. var is not a keyword but indicates that the type of a local variable should be type-inferred.
 *    i.e. it is used for local variable type inference.
 * 
 * 2. local variable type inference means the type of for the variable will be infered by compiler.
 * 
 * 3. It reduces boilerplate code and increase code readability.
 * 
 * 4. var keyword has a static type which is the declared type of value. This means that assigning a value of 
 *    a different type will always fail. Hence, Java is still a statically typed language (unlike JavaScript).
 * 
 * 5. In inheritance scenario, a variable declared with var is always the type of initializer and var may not
 *    be used when there is no initializer. So, polymorphic behavior does not work with var keyword. 
 * 
 * 6. We can't use local variable type inference with method arguments.
 * 
 * 7. We cannot initialize a var variable to null. By assigning null, it is not clear what the type should be, 
 *    since in Java.
 * 
 * 8. You can't use local variable type inference with lambda expressions, because those require an explicit 
 *    target type. Because lambda expressions need explicit target type.
 * 
 */
class Person {}
class Doctor extends Person {}
class Engineer extends Person {}

public class VarDemo {
    public static void main(String[] args) {
        // Map<String, String> map = new HashMap<String, String>();
        // Map<String, List<String>> listOfMovies = new HashMap<String, List<String>>();
        // above statements can be written as below with var keyword argument
        var map = new HashMap<String, String>();
        var listOfMovies = new HashMap<String, List<String>>();
        var id = 0;
        // var id = "33" // throws compilation error Refer point 4.

        var p = new Doctor(); // p is always the type of Doctor
        // p = new Engineer(); // compilation error because of incompatible type refer point 5.

        // var person = null; // compilation error refer point 7.

        // var z = () -> {}; // compilation error refer point 8.
    }

    /*
    Compilation error because compiler cannot infer type of local variable lines; 
    cannot use 'var' on variable without initializer. refer point 6.
    */
    // public long countLines(var lines) // compilation error 
}