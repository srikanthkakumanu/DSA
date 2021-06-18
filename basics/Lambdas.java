package basics;

import java.util.ArrayList;

/**
 * Lambda expression support added since Java 8.0.
 *  1. A lambda expression is essentially a function that does not have a name, and can be treated 
 *      as a value i.e. literals in the language. A lambda expression is an unnamed block of code
 *      (with optional parameters) that can be stored, passed around & executed later. 
 *  
 * 2. As Java does not allow code to run around on its own outside of classes, in Java, this means 
 *      that a lambda is an 'anonymous method' that is defined on some class 
 *      (that is possibly unknown to the developer).
 * 
 * 3. When using Lambda expressions, If a function that expects a return value, if there is only one 
 *      statement exists in lambda expression block, then return statement is optional. Otherwise, 
 *      you must use return statement. i.e. lambda expression block contains multiple statements and 
 *      that expects a return statement.
 * 
 * 4. Infact lambdas are implemented using method handles and a new special JVM bytecode called 'invokedynamic'.
 * 
 * 5. lambda expressions can access ' effectively final' variables from the enclosing scope 
 *      i.e. a variable declared within the method or enclosing block.  
 * 
 * 6. Advantages: Lambda uses variable type inference capabilities.
 * 
 * 7. Advantages: Lambdas are most effective when they are 'stateless' & no shared mutable data.
 * 
 * 8. Advantages: Lambda expressions can implement (simplified) variants of 'closures'. Check: LambdaClosures.java
 * 
 * 9. Advantages: Lambda expressions are concise and flexible(compared to method references(Java 8)).
 */
interface Lambda {
    public void todo();
}

interface LambdaWithSingleParams {
    public void todoWithSingleParams(String name);
}

interface LambdaWithMultiParams {
    public void todoWithMultiParams(int first, int second);
}

public class Lambdas {
    public static void main(String[] args) {

        withoutLambda();
        withLambda(); 
        lambdaWithSingleParams();
        lambdaWithMultiParams();
        lambdaWithForEach();
    }

    /**
     * With out Lambda expression
     */
    public static void withoutLambda() {
        Lambda lambda = new Lambda() {
            public void todo() {
                System.out.println("TODO: without lambda");
            }
        };
        lambda.todo();
    }

    /**
     * With Lambda expression
     */
    public static void withLambda() {
        Lambda lambda = () -> { 
            System.out.println("TODO: with Lambda");
        };
        lambda.todo();
    }
    /**
     * Lambda expression with Single parameter
     */
    public static void lambdaWithSingleParams() {

        // LambdaWithSingleParams withParams = name -> {
        //     System.out.println("TODO: Lambda with Single Params : " + name);
        // };

        LambdaWithSingleParams withParams = (name) -> {
            System.out.println("TODO: Lambda with Single Params : " + name);
        };
        withParams.todoWithSingleParams("Srikanth");
    }

    /**
     * Lambda expression with multiple parameters
     */
    public static void lambdaWithMultiParams() {
        LambdaWithMultiParams withMultiParams = (first, second) -> {
            System.out.println("TODO: lambda with multiple params without return statement: " + (first + second));
        };

        // LambdaWithMultiParams withMultiParams1 = (first, second) -> 
        //     System.out.println("TODO: lambda with multiple params without return statement: " + (first + second));

        // LambdaWithMultiParams withMultiParams = (int first, int second) -> 
        //     System.out.println("TODO: lambda with multiple params without return statement: " + (first + second));
        
        withMultiParams.todoWithMultiParams(20, 30);
    }

    /**
     * Lambda expression with for-each loop
     */
    public static void lambdaWithForEach() {
        var list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");

        list.forEach(value -> System.out.println(value));
    }

}
