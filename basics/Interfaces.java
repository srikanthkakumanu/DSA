package basics;

/**
 * 1. interfaces cannot contain constructors as it is not a class type.
 * 2. All fields of an interface, implicitly constants i.e. 'public', 'static' and 'final'.
 * 
 * 3. Methods: All mandatory methods of an interface, implicitly abstract and public. 
 *      3.1 Only ('public' or 'private'), ('abstract' or 'default' or 'static'), 'strictfp' methods are allowed.
 *      3.2 inteface can contain static(Java 8), private(Java 9) methods but previous versions did not allow this.
 *      3.3 Only one of 'abstract' or ('public' or 'private') or 'static' methods permitted in interface methods.
 *      3.4 interface static methods do not get overridden by implementation class.
 *      3.5 interfaces cannot have final methods.
 * 
 * 4. Default Methods: default (optional) methods are introduced in Java 8. 
 *      4.1 methods marked as 'default' in an interface are implicitly 'public', 
 *          need not be implemented by implementation classes. 
 *      4.2 But interface must provide an implementation to these 'default' methods. 
 *      4.3 default methods cannot be 'abstract' or 'static'.
 *      4.4 default advantage is: not breaking legecy code or backward compatibility.
 * 
 * 5. Nested Types: interfaces can have nested types i.e. inner interfaces and classes. 
 *      5.1. all nested types are implicitly public.
 *      5.2. only public, abstract, static and strictfp are permitted for nested member interface or class.
 *      5.3. nested member classes can combine static, abstract or final, strictfp.
 * 
 * 6. interface can extend more than one interface unlike classes.
 */
interface Print {
    int x = 10, y = 20; 
    static void draw(int x, int y) { // Java 8
        System.out.println("Print: x=" + x + ", y=" + y);
    }


    default void draw() { // Java 8
        System.out.println("Default: Print.draw() method");
    }

    interface PrintInnerInterface {
        int i = 30, j = 40;
    }

    static final strictfp class PrintInnerClass {
        void draw() {
            System.out.println("Print.PrintInnerClass.draw()");
        }
    }


}
class PrintImpl implements Print {
    static void draw(int x, int y) {
        System.out.println("PrintImpl: x=" + x + ", y=" + y);
    }
}

public class Interfaces {
    public static void main(String[] args) {
        Print p = new PrintImpl();
        PrintImpl pp = new PrintImpl();

        System.out.println("Print.x=" + Print.x); // calling interface constant field
        Print.draw(100, 200); // calling interface static method
        // calling static method in impl class with instance. interface static methods do not get overridden by impl classes.
        PrintImpl.draw(1200, 4200); pp.draw(344, 6);
        p.draw(); // calling interface default method 
        // nested types: inner interface
        System.out.println("Print.PrintInnerInterface: i=" + Print.PrintInnerInterface.i + ", j=" + Print.PrintInnerInterface.j);
        // nested types: inner class
        Print.PrintInnerClass ip = new Print.PrintInnerClass();
        ip.draw();
    }   
}
