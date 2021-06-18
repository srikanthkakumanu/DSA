package basics;

import java.util.function.BiFunction;

/**
 * Method References are introduced in Java 8.
 * 1. Method references are simple, concise to use. We use :: operator for method references.
 * 
 * 2. Method reference is used to refer method of functional interface. It is concise/compact
 *      and easy form of lambda expression.
 * 
 * 3. Types of method references are: static method reference, instance method reference and 
 *      constructor reference.
 * 
 * 4. Syntax: class::methodName or instance::methodName or class::new
 */
public class MethodRefs {
    public static void main(String[] args) {
        // static method-reference
        Box box = MethodRefs::packClothes; // injecting packClothes behaviour into Box interface pack() method.
        box.pack();
        box = MethodRefs::packToys; // injecting packToys behaviour into Box interface pack() method.
        box.pack();
        perform(); // Using BiFunction interface to refer methods.
        
        // instance based method reference
        Arithmetic calc =new Arithmetic();
        Calculator calculator = calc::multiply;
        System.out.println(calculator.calculate(10, 20));
        calculator = calc::division;
        System.out.println(calculator.calculate(100, 20));

        // constructor based method references
        Messager message = Message::new;
        message.getMessage("Hello World!");
    }

    static void packClothes() {
        System.out.println("Pack clothes in Box");
    }

    static void packToys() {
        System.out.println("Pack Toys in Box");
    }

    /**
     * Using pre-defined BiFunction functional interface to refer methods.
     */
    static void perform() {
        BiFunction<Integer, Integer, Integer> adder = Arithmetic::add;
        System.out.println(adder.apply(200, 300));
        BiFunction<Float, Float, Float> floatAdder = Arithmetic::add;
        System.out.println(floatAdder.apply(2.0f, 3.0f));
    }

}

@FunctionalInterface // optional
interface Box {
    void pack();
}

@FunctionalInterface // optional
interface Calculator {
    public int calculate(int a, int b);
}

class Arithmetic {
    public static int add(int a, int b) { return a + b; }
    public static float add(float a, float b) { return a + b; }
    public int multiply(int a, int b) { return a * b; }
    public int division(int a, int b) { return a / b; }
}

@FunctionalInterface // optional
interface Messager {
    Message getMessage(String message);
}

class Message {
    Message(String message) { System.out.println(message + ": is a message from Message"); }
}