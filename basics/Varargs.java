package basics;

/**
 * 1. Varargs paraemter internally represent as an array.
 * 2. Varargs paraemter always must/should be the last paraemter in the argument list. 
 */
public class Varargs {
    public static void main(String[] args) {
        print(3, 49, 50, 28, 500, 383, 49030, 5948, 328);
    }

    // static void print(int... set, int check) { // refer point 1.
    static void print(int check, int... set) {    
        for(int value : set)
            System.out.println(value);
    }

    // static void printAll(int... check, int... set) {} // compiler error i.e. cannot have more than one varargs param
}
