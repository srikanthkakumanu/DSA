package basics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * 1. Generics introduced in Java 5. Since Java 7, type inference '<>' operator is introduced.
 * 
 * 2. Advantages are type safety, type casting is not required and compile-time type check.
 * 
 * 3. Generics allows a class/interface or method to introduce 'type-parameters' or 
 *      'parameterized-type' e.g. T, E, K, V etc, which are symbols that can be 
 *      substituted for any concrete type. type-parameters are always for reference types.
 *      It is not possible to use a primitive type as a value for type-parameter.
 * 
 * 4. Bounded type parameters always use 'extends' keyword even with interface. 
 * 
 * 5. 
 */
public class Generics {
    public static void main(String[] args) {
        // 1. simple usage
        simple();
        // 2. type parameters at class/interface level.
        genericTypes(); 
        // 3. type parameters at method level.
        List<String> strings = List.of("first", "second");
        List<Integer> integers = List.of(4, 6);
        List<Human> humans = List.of(new Human("Tarun"), new Human("Elton"));
        System.out.println(genericTypeAtMethodLevel(strings));
        System.out.println(genericTypeAtMethodLevel(integers));
        System.out.println(genericTypeAtMethodLevel(humans));
        // 4. bounded generic types
        boundedTypes();
    }

    /**
     * Simple usage of generic type and type inference operator '<>'.
     */
    static void simple() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2"); // set.add(2) compile-time type check and type safety
        String value = list.get(1); // no type-casting required
        System.out.println(value);
    }

    /**
     * Demonstrates Generic Types at class/interface level.
     */
    static void genericTypes() {
        // can use any type
        // single type example
        // Pair<String> strings = new Pair<>("srikanth", "kakumanu"); 
        // Pair<Integer> integers = new Pair<>(2, 4); // can use any concrete type 
        // Pair<Human> humans = new Pair<>(
        //                             new Human("Brendon"), 
        //                             new Human("Elton")); // can use any concrete type
        
        // multitple types
        Pair<String, String> strings = new Pair<>("Srikanth", "Kakumanu");
        Pair<Integer, Integer> integers = new Pair<>(32, 54);
        Pair<String, Integer> multi = new Pair<>("Srikanth", 56);
        Pair<Human, Human> humans = new Pair<>(new Human("Brendon"), new Human("Elton"));
        Pair<Human, Integer> hybrid = new Pair<>(new Human("Tarun"), 45);
        String first = multi.getFirst();
        Integer second = multi.getSecond();

        System.out.println(strings);
        System.out.println(integers);
        System.out.println(multi);
        System.out.println(humans);
        System.out.println(hybrid);
        System.out.println("first=" + first + ", second=" + second);
    }

    /**
     * Demonstrates Generic types at method level.
     */
    static <T> List<T> genericTypeAtMethodLevel(List<T> original) {
        List<T> reverse = new ArrayList<>(original);
        Collections.reverse(reverse);
        return reverse;
    }

    /**
     * Demonstrates Generic Types extends a specific type i.e. bounded
     * 
     */
    static void boundedTypes() {
        BoundedTypePair<Integer> integers = new BoundedTypePair<>(29, 68);
        // BoundedTypePair<String> strings = 
        //             new BoundedTypePair<>("Srikanth", "Kakumanu"); // compile-time error, can use only numbers i.e. bounded type
        System.out.println(integers);

    }
}

class BoundedTypePair<T extends Number> {
    private T first;
    private T second;

    public BoundedTypePair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public T getSecond() {
        return second;
    }
    public void setSecond(T second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BoundedTypePair.class.getSimpleName() + "[", "]")
                .add("first='" + first + "'")
                .add("second='" + second + "'")
                .toString();
    }
}

class Pair<T, S> {
    private T first;
    private S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public S getSecond() {
        return second;
    }
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pair.class.getSimpleName() + "[", "]")
                .add("first='" + first + "'")
                .add("second='" + second + "'")
                .toString();
    }
}

// class Pair<T> {
//     private T first;
//     private T second;

//     public Pair(T first, T second) {
//         this.first = first;
//         this.second = second;
//     }

//     public T getFirst() {
//         return first;
//     }
//     public void setFirst(T first) {
//         this.first = first;
//     }
//     public T getSecond() {
//         return second;
//     }
//     public void setSecond(T second) {
//         this.second = second;
//     }

//     @Override
//     public String toString() {
//         return new StringJoiner(", ", Pair.class.getSimpleName() + "[", "]")
//                 .add("first='" + first + "'")
//                 .add("second='" + second + "'")
//                 .toString();
//     }
// }

class Human {
    private String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Human.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
