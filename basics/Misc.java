package basics;
/**
 * This example demonstrates:
 * 1. pass by value and pass by reference with regard to
 *    primitive values and object references.
 * 2. demonstrates equality of objects and values equals() and == 
 */
public class Misc {
    public static void main(String[] args) {
        // For primitive types, Java copies the value i.e. x and y hold two different copies of same value in JVM memory
        int x = 42;
        int y = x;
        x++;
        System.out.println("x=" + x + ", y=" + y);
        passPrimitive(x, y); // x value does not change since we passed value into the method
        System.out.println("x=" + x + ", y=" + y);
        /*
            For reference types, Java holds a copy of reference that points to same Point object in JVM memory
            the variable q holds a copy of the reference held in the variable p. There is
            still only one copy of the Point object in the VM, but there are now two copies of the
            reference to that object.
        */
        Point p = new Point(1.0f, 2.0f);
        Point q = p;
        System.out.println(p.x);
        q.x = 13.0f;
        System.out.println(p.x);
        passReference(p); // x value changed since we passed p object reference into the method
        System.out.println(p.x); 

        // test == with primitives and object references. equals() method with object references
        equality();
    }

    static void passPrimitive(int x, int y) { 
        x = 10; y = 30;
        System.out.println("passPrimitive: x=" + x + ", y=" + y);
    }

    static void passReference(Point p) {
        p.x = 123.0f;
        System.out.println("passReference: p.x=" + p.x);
    }

    static void equality() {
        /*
            == with primitive values, it tests whether two values are identical
            == with references, it compares two object references not actual values
            i.e. it simply tests whether two references refer to same object.
        */
        // == operator with primitives
        int a = 10, b = 10;
        System.out.print("a and b are ");
        
        if(a == b) 
            System.out.println("equal");
        else
            System.out.println("not equal");
        
        // == operator with references
        String letter = "o";
        String s = "hello";
        String greeting = "hell" + letter;
        System.out.print("s and greeting are ");
        
        if(s == greeting)
            System.out.println("identical");
        else
            System.out.println("not identical");
        
        /*
            equals method need to be overridden if they have to compare values also.
            So far, String class does the value comparison as well. 
        */
        // equals()` method with references
        System.out.print("s and greeting are ");
        if(s.equals(greeting))
            System.out.println("equal");
        else
            System.out.println("not equal");

        // equals() method wih arrays    
        int k[] = {1, 2, 3}; int j[] = {1, 2, 3};
        System.out.print("k and j are ");
        
        if(k == j)
            System.out.println("identical");
        else
            System.out.println("not identical");

        System.out.print("k and j are ");
        if(k.equals(j))
            System.out.println("equal");
        else
            System.out.println("not equal");
    }
}

class Point {
    float x, y;
    Point(float x, float y) { this.x = x; this.y = y; }
}