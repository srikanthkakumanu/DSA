package basics;

import java.util.function.Function;

/**
 * Lambda expressions can implement simplified closures in Java 8.
 * 1. Closures are the inline-function valued expressions, they are nothing but stateful functions. 
 *      A closure is a combination of a function bundled together (enclosed) with references to 
 *      its surrounding state.
 * 
 * 2. A Closure gives you access to an outer function's scope from an inner function. 
 *      Uses of Closures include data privacy, partial application and currying. 
 * 
 * 3. Currying means breaking a function with many parameters as a number of functions 
 *      in a single argument
 * 
 * 4. Syntax to implement Closure: (argument_list) -> {func_body}
 * 
 * 
 */
public class LambdaClosures {
    public static void main(String[] args) {
        Usage1();
        Usage2();
        Usage3();
        Usage4();
        Usage5();
    }

    /**
     * Type-1:
     *  s is a closure because it bundles say() function.  
     */
    static void Usage1() {
        Salutation s = () -> { return "Hi!"; };
        System.out.println(s.say());
    }

    /**
     * Type-2:
     * Using custom functional interface and lambda expression.
     */
    static void Usage2() {
        SalutationWithParam s = name -> { return "Hi " + name; };
        System.out.println(s.say("Srikanth"));
    }

    /**
     * Type-3:
     * Using pre-defined functional interface i.e. java.util.function.Function<T,R> 
     * and lambda expression.
     * 1. It avoids the need to write a custom functional interface because it
     *      uses a pre-defined Function interface.
     * 2. It is in fact, an anti-pattern to write custom functional interfaces 
     *      for those that Java has predefined.
     */
    static void Usage3() { 
        Function<String, String> s = name -> { return "Hi " + name; };
        System.out.println(s.apply("Srikanth"));
    }

    /**
     * Type-4:
     * 1. It returns an inner function in the form of lambda expression that encloses
     *      'name' which is in the scope of parent function i.e. func().
     * 2. Using pre-defined functional interface i.e. java.util.function.Function<T,R>,
     *      lambda expression and inner function having access to parent.
     */
    static Function<String, String> func() {
        return name -> "Hi " + name;
    }

    static void Usage4() {
        System.out.println(func().apply("Srikanth"));
    }

    /**
     * Type-5:
     * 1. Using pre-defined functional interface i.e. java.util.function.Function<T,R>,
     *      lambda expression, inner function having access to parent scope which is
     *      nothing but state passed by client.
     * 
     */
    static Function<String, String> func(String title) {
        return name -> "Hi " + title + " " + name;
    }

    static void Usage5() {
        Function<String, String> s1 = func("Dr.");
        System.out.println(s1.apply("Srikanth"));
    }
}

// @FunctionalInterface is optional
interface Salutation {
    public String say();
}

// @FunctionalInterface is optional
interface SalutationWithParam {
    public String say(String name);
}



